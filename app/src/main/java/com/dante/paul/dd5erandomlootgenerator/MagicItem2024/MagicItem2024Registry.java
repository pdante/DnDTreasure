package com.dante.paul.dd5erandomlootgenerator.MagicItem2024;

import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemRarity;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemTheme;

public final class MagicItem2024Registry {

    private MagicItem2024Registry() {}

    public static MagicItem2024Table getTable(MagicItemTheme theme, MagicItemRarity rarity) {
        MagicItemTheme resolved = (theme == MagicItemTheme.RANDOM || theme == null)
                ? MagicItemTheme.ARCANA : theme;
        switch (resolved) {
            case ARCANA:
                return getArcanaTable(rarity);
            case ARMAMENTS:
            case IMPLEMENTS:
            case RELICS:
            default:
                return getArcanaTable(rarity);
        }
    }

    public static boolean isThemeImplemented(MagicItemTheme theme) {
        if (theme == null) return false;
        switch (theme) {
            case ARCANA:
            case RANDOM:
                return true;
            default:
                return false;
        }
    }

    private static MagicItem2024Table getArcanaTable(MagicItemRarity rarity) {
        switch (rarity) {
            case COMMON: return ArcanaTables.COMMON;
            case UNCOMMON: return ArcanaTables.UNCOMMON;
            case RARE: return ArcanaTables.RARE;
            case VERY_RARE: return ArcanaTables.VERY_RARE;
            case LEGENDARY: return ArcanaTables.LEGENDARY;
            default: return ArcanaTables.COMMON;
        }
    }
}
