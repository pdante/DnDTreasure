package com.verdantsoftware.lootforge.TreasureCreationClasses;

import com.verdantsoftware.lootforge.Dice.Dice;
import com.verdantsoftware.lootforge.EnumeratedClasses.ChallengeRating;
import com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemRarity;
import com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemTheme;
import com.verdantsoftware.lootforge.EnumeratedClasses.TierOfPlay;
import com.verdantsoftware.lootforge.LootList;
import com.verdantsoftware.lootforge.MagicItem2024.MagicItem2024Generator;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.JustMagicItems;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.MagicItemTableObject;

import java.util.ArrayList;
import java.util.List;

public class GenerateItem implements TreasureTable {
    protected ChallengeRating challengeRating;
    protected int d100;
    protected Dice d = new Dice();
    public LootList list;
    int numberOfItems;

    private TierOfPlay partyTier;
    private MagicItemTheme theme;
    private boolean use2024Rules;
    private final int[] generatedRarityCounts = new int[MagicItemRarity.values().length];
    private final List<MagicItem2024Generator.Result> generatedItems = new ArrayList<>();

    public GenerateItem(ChallengeRating challengeRating, int numberOfItems) {
        this.numberOfItems = numberOfItems;
        this.challengeRating = challengeRating;
        this.list = LootList.getInstance();
        this.use2024Rules = false;
    }

    public GenerateItem(TierOfPlay partyTier, MagicItemTheme theme, int numberOfItems) {
        this.partyTier = partyTier;
        this.theme = theme;
        this.numberOfItems = numberOfItems;
        this.list = LootList.getInstance();
        this.use2024Rules = true;
    }

    @Override
    public void generateTreasure() {
        if (use2024Rules) {
            generate2024Items();
        } else {
            generate2014Items();
        }
        list.getItems();
    }

    private void generate2014Items() {
        for (int counter = 0; counter < numberOfItems; counter++) {
            d100 = d.roll(100);
            JustMagicItems items = new JustMagicItems(challengeRating, d100);
            items.createStuff();
        }
    }

    private void generate2024Items() {
        MagicItem2024Generator generator = new MagicItem2024Generator();
        for (int counter = 0; counter < numberOfItems; counter++) {
            MagicItem2024Generator.Result result = generator.generate(partyTier, theme);
            generatedRarityCounts[result.rarity.ordinal()]++;
            generatedItems.add(result);
            MagicItemTableObject obj = new MagicItemTableObject();
            GenerateItemStrings strings = new GenerateItemStrings();
            strings.setName(result.itemName);
            strings.setMagicItemtable(
                    "(" + themeLabel(result.theme) + " — " + rarityLabel(result.rarity) + ")");
            obj.generatedStrings = strings;
            list.addToLoot(obj);
        }
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
