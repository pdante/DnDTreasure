package com.dante.paul.dd5erandomlootgenerator.MagicItem2024;

import com.dante.paul.dd5erandomlootgenerator.Dice.Dice;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemRarity;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemTheme;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.TierOfPlay;

public final class MagicItem2024Generator {

    public static class Result {
        public final String itemName;
        public final MagicItemRarity rarity;
        public final MagicItemTheme theme;

        public Result(String itemName, MagicItemRarity rarity, MagicItemTheme theme) {
            this.itemName = itemName;
            this.rarity = rarity;
            this.theme = theme;
        }
    }

    private final Dice dice = new Dice();

    public Result generate(TierOfPlay tier, MagicItemTheme requestedTheme) {
        MagicItemTheme theme = resolveTheme(requestedTheme);
        MagicItemRarity rarity = RarityRoller.rollRarity(tier, dice.roll(100));
        MagicItem2024Table table = MagicItem2024Registry.getTable(theme, rarity);
        String name = table.roll(dice.roll(100));
        return new Result(name, rarity, theme);
    }

    private MagicItemTheme resolveTheme(MagicItemTheme requestedTheme) {
        if (requestedTheme != null && requestedTheme != MagicItemTheme.RANDOM) {
            return requestedTheme;
        }
        int r = dice.roll(4);
        switch (r) {
            case 1: return MagicItemTheme.ARCANA;
            case 2: return MagicItemTheme.ARMAMENTS;
            case 3: return MagicItemTheme.IMPLEMENTS;
            default: return MagicItemTheme.RELICS;
        }
    }
}
