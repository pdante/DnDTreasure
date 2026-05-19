package com.dante.paul.dd5erandomlootgenerator.TreasureCreationClasses;

import com.dante.paul.dd5erandomlootgenerator.Dice.Dice;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.ChallengeRating;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemRarity;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemTheme;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.TierOfPlay;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.TypeOfEncounter;
import com.dante.paul.dd5erandomlootgenerator.LootList;
import com.dante.paul.dd5erandomlootgenerator.MagicItem2024.MagicItem2024Generator;
import com.dante.paul.dd5erandomlootgenerator.MagicItem2024.Random2024Treasure;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.MagicItemArtAndGemTables.GemsArtAndMagicItems;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.MagicItemArtAndGemTables.HoardCoins;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.MagicItemArtAndGemTables.IndividualCoins;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.TableObjects.MagicItemTableObject;

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
        if (use2024Rules) {
            generate2024();
        } else {
            generate2014();
        }
        list.getTreasure();
    }

    private void generate2014() {
        for (int counter = 0; counter < numberOfIterations; counter++) {
            d100 = d.roll(100);
            if (toE == TypeOfEncounter.INDIVIDUAL) {
                new IndividualCoins(challengeRating, d100).createStuff();
            } else {
                new HoardCoins(challengeRating, d100).createStuff();
                new GemsArtAndMagicItems(challengeRating, d100).createStuff();
            }
        }
    }

    private void generate2024() {
        for (int counter = 0; counter < numberOfIterations; counter++) {
            if (toE == TypeOfEncounter.INDIVIDUAL) {
                Random2024Treasure.IndividualResult money =
                        Random2024Treasure.rollIndividual(challengeRating);
                list.addToCoins(currencyLabel(money.currency), money.amount);
            } else {
                Random2024Treasure.HoardResult hoard =
                        Random2024Treasure.rollHoard(challengeRating);
                list.addToCoins(currencyLabel(hoard.currency), hoard.amount);
                MagicItem2024Generator generator = new MagicItem2024Generator();
                for (int i = 0; i < hoard.magicItemCount; i++) {
                    MagicItem2024Generator.Result result = generator.generate(partyTier, theme);
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
    }

    private String currencyLabel(Random2024Treasure.Currency c) {
        return c == Random2024Treasure.Currency.PP ? "PP" : "GP";
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
