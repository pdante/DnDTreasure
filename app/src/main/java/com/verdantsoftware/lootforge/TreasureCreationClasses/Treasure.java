package com.verdantsoftware.lootforge.TreasureCreationClasses;

import com.verdantsoftware.lootforge.Dice.Dice;
import com.verdantsoftware.lootforge.EnumeratedClasses.ChallengeRating;
import com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemRarity;
import com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemTheme;
import com.verdantsoftware.lootforge.EnumeratedClasses.TierOfPlay;
import com.verdantsoftware.lootforge.EnumeratedClasses.TypeOfEncounter;
import com.verdantsoftware.lootforge.LootList;
import com.verdantsoftware.lootforge.MagicItem2024.MagicItem2024Generator;
import com.verdantsoftware.lootforge.MagicItem2024.Random2024Treasure;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.GemsArtAndMagicItems;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.HoardCoins;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.IndividualCoins;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.MagicItemTableObject;

import java.util.ArrayList;
import java.util.List;

public class Treasure implements TreasureTable {
    protected ChallengeRating challengeRating;
    protected int d100;
    protected Dice d = new Dice();
    protected TypeOfEncounter toE;
    public LootList list;
    int numberOfIterations;

    // 2024-mode fields
    private TierOfPlay partyTier;
    private MagicItemTheme theme;
    private boolean use2024Rules;
    private final int[] generatedRarityCounts = new int[MagicItemRarity.values().length];
    private final List<MagicItem2024Generator.Result> generatedItems = new ArrayList<>();

    /**
     * Parallel to {@link #generatedItems}: which iteration each item came from
     * (0-indexed). Used by the result dialog to group items by hoard.
     */
    private final List<Integer> itemHoardIndex = new ArrayList<>();
    /** One formatted coin summary per hoard (e.g. "300gp"), in iteration order. */
    private final List<String> hoardCoinSummaries = new ArrayList<>();

    /**
     * Populated only when we render each hoard iteration separately
     * (HORDE + numberOfIterations > 1). When null, callers fall back to
     * {@link LootList#getTreasure()} which holds the merged result.
     */
    private String perHoardOutput;

    private static final String HOARD_DIVIDER =
            "\r\n────────────────────────────\r\n";

    public Treasure(ChallengeRating challengeRating, TypeOfEncounter toE, int numberOfIterations) {
        this.challengeRating = challengeRating;
        list = LootList.getInstance();
        this.toE = toE;
        this.numberOfIterations = numberOfIterations;
        this.use2024Rules = false;
    }

    public Treasure(ChallengeRating challengeRating,
                    TypeOfEncounter toE,
                    TierOfPlay partyTier,
                    MagicItemTheme theme,
                    int numberOfIterations) {
        this.challengeRating = challengeRating;
        this.toE = toE;
        this.partyTier = partyTier;
        this.theme = theme;
        this.numberOfIterations = numberOfIterations;
        this.list = LootList.getInstance();
        this.use2024Rules = true;
    }

    public void generateTreasure() {
        // For multi-iteration hoards, render each hoard separately with a
        // divider between them. Individual treasure and single-iteration
        // hoards keep the existing merged-pile behavior.
        if (toE == TypeOfEncounter.HORDE && numberOfIterations > 1) {
            StringBuilder out = new StringBuilder();
            for (int counter = 0; counter < numberOfIterations; counter++) {
                list.deleteAll();
                if (use2024Rules) {
                    generateOneIteration2024(counter);
                } else {
                    generateOneIteration2014(counter);
                }
                if (counter > 0) out.append(HOARD_DIVIDER);
                out.append("Hoard ")
                        .append(counter + 1)
                        .append(" of ")
                        .append(numberOfIterations)
                        .append(":\r\n");
                out.append(list.getTreasure());
            }
            perHoardOutput = out.toString();
        } else {
            for (int counter = 0; counter < numberOfIterations; counter++) {
                if (use2024Rules) {
                    generateOneIteration2024(counter);
                } else {
                    generateOneIteration2014(counter);
                }
            }
            list.getTreasure();
        }
    }

    /** Single iteration of 2014-rules generation. */
    private void generateOneIteration2014(int iterationIndex) {
        d100 = d.roll(100);
        if (toE == TypeOfEncounter.INDIVIDUAL) {
            new IndividualCoins(challengeRating, d100).createStuff();
        } else {
            new HoardCoins(challengeRating, d100).createStuff();
            new GemsArtAndMagicItems(challengeRating, d100).createStuff();
        }
    }

    /** Single iteration of 2024-rules generation. */
    private void generateOneIteration2024(int iterationIndex) {
        if (toE == TypeOfEncounter.INDIVIDUAL) {
            Random2024Treasure.IndividualResult money =
                    Random2024Treasure.rollIndividual(challengeRating);
            list.addToCoins(currencyLabel(money.currency), money.amount);
        } else {
            Random2024Treasure.HoardResult hoard =
                    Random2024Treasure.rollHoard(challengeRating);
            list.addToCoins(currencyLabel(hoard.currency), hoard.amount);
            hoardCoinSummaries.add(hoard.amount + currencyLabel(hoard.currency));
            MagicItem2024Generator generator = new MagicItem2024Generator();
            for (int i = 0; i < hoard.magicItemCount; i++) {
                MagicItem2024Generator.Result result = generator.generate(partyTier, theme);
                generatedRarityCounts[result.rarity.ordinal()]++;
                generatedItems.add(result);
                itemHoardIndex.add(iterationIndex);
                MagicItemTableObject obj = new MagicItemTableObject();
                GenerateItemStrings strings = new GenerateItemStrings();
                strings.setName(result.itemName);
                strings.setMagicItemtable(
                        "(" + themeLabel(result.theme) + " — " + rarityLabel(result.rarity) + ")");
                obj.generatedStrings = strings;
                list.addToLoot(obj);
            }
        }
    }

    /** Parallel to {@link #getGeneratedItems()}: hoard index (0-based) per item. */
    public int[] getItemHoardIndices() {
        int[] arr = new int[itemHoardIndex.size()];
        for (int i = 0; i < arr.length; i++) arr[i] = itemHoardIndex.get(i);
        return arr;
    }

    /** Per-hoard coin summary strings (e.g. "300gp"), in iteration order. */
    public String[] getHoardCoinSummaries() {
        return hoardCoinSummaries.toArray(new String[0]);
    }

    /**
     * Final formatted output for the result dialog.
     * Returns the per-hoard rendered string for multi-iteration hoards,
     * or the merged LootList for individual / single-iteration runs.
     */
    public String getOutput() {
        return perHoardOutput != null ? perHoardOutput : list.getTreasure();
    }

    public int[] getGeneratedRarityCounts() {
        return generatedRarityCounts.clone();
    }

    public List<MagicItem2024Generator.Result> getGeneratedItems() {
        return generatedItems;
    }

    public TierOfPlay getPartyTier() {
        return partyTier;
    }

    private String currencyLabel(Random2024Treasure.Currency c) {
        return c == Random2024Treasure.Currency.PP ? "pp" : "gp";
    }

    private String rarityLabel(MagicItemRarity rarity) {
        switch (rarity) {
            case COMMON: return "Common";
            case UNCOMMON: return "Uncommon";
            case RARE: return "Rare";
            case VERY_RARE: return "Very Rare";
            case LEGENDARY: return "Legendary";
            default: return "Magic Item";
        }
    }

    private String themeLabel(MagicItemTheme theme) {
        if (theme == null) return "Random";
        switch (theme) {
            case ARCANA: return "Arcana";
            case ARMAMENTS: return "Armaments";
            case IMPLEMENTS: return "Implements";
            case RELICS: return "Relics";
            default: return "Random";
        }
    }
}
