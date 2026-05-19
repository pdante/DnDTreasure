package com.dante.paul.dd5erandomlootgenerator.TreasureCreationClasses;

import com.dante.paul.dd5erandomlootgenerator.Dice.Dice;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.ChallengeRating;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemRarity;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemTheme;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.TierOfPlay;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.TypeOfEncounter;
import com.dante.paul.dd5erandomlootgenerator.LootList;
import com.dante.paul.dd5erandomlootgenerator.MagicItem2024.MagicItem2024Generator;
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

    private TierOfPlay tier;
    private MagicItemTheme theme;
    private boolean use2024Magic;

    public Treasure(ChallengeRating challengeRating, TypeOfEncounter toE, int numberOfIterations) {
        this.challengeRating = challengeRating;
        list = LootList.getInstance();
        this.toE = toE;
        this.numberOfIterations = numberOfIterations;
        this.use2024Magic = false;
    }

    public Treasure(TierOfPlay tier, MagicItemTheme theme, int numberOfIterations) {
        this.tier = tier;
        this.theme = theme;
        this.challengeRating = tier.toChallengeRating();
        this.toE = TypeOfEncounter.HORDE;
        this.numberOfIterations = numberOfIterations;
        this.list = LootList.getInstance();
        this.use2024Magic = true;
    }

    public void generateTreasure(){
        for (int counter = 0; counter < numberOfIterations; counter ++) {
            d100 = d.roll(100);
            generateCoins();
            if (toE == TypeOfEncounter.HORDE) {
                if (use2024Magic) {
                    generate2024Items();
                } else {
                    generateItems();
                }
            }
        }
        list.getTreasure();
    }

    private void generateCoins() {
        if (toE == TypeOfEncounter.INDIVIDUAL) {
            IndividualCoins coins = new IndividualCoins(challengeRating, d100);
            coins.createStuff();
        } else {
            HoardCoins coins = new HoardCoins(challengeRating, d100);
            coins.createStuff();
        }
    }

    private void generateItems(){
        GemsArtAndMagicItems items = new GemsArtAndMagicItems(challengeRating, d100);
        items.createStuff();
    }

    private void generate2024Items() {
        int itemCount = countMagicItemsForTier(tier);
        MagicItem2024Generator generator = new MagicItem2024Generator();
        for (int i = 0; i < itemCount; i++) {
            MagicItem2024Generator.Result result = generator.generate(tier, theme);
            MagicItemTableObject obj = new MagicItemTableObject();
            GenerateItemStrings strings = new GenerateItemStrings();
            strings.setName(result.itemName);
            strings.setMagicItemtable("(" + themeLabel(result.theme) + " — " + rarityLabel(result.rarity) + ")");
            obj.generatedStrings = strings;
            list.addToLoot(obj);
        }
    }

    private int countMagicItemsForTier(TierOfPlay tier) {
        switch (tier) {
            case TIER_1: return 1;
            case TIER_2: return 2;
            case TIER_3: return 3;
            case TIER_4: return 4;
            default: return 1;
        }
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
        switch (theme) {
            case ARCANA: return "Arcana";
            case ARMAMENTS: return "Armaments";
            case IMPLEMENTS: return "Implements";
            case RELICS: return "Relics";
            default: return "Random";
        }
    }
}
