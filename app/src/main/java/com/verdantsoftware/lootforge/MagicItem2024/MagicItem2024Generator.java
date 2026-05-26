package com.verdantsoftware.lootforge.MagicItem2024;

import com.verdantsoftware.lootforge.Dice.Dice;
import com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemRarity;
import com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemTheme;
import com.verdantsoftware.lootforge.EnumeratedClasses.TierOfPlay;

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
