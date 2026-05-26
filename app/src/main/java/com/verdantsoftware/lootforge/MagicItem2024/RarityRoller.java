package com.verdantsoftware.lootforge.MagicItem2024;

import com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemRarity;
import com.verdantsoftware.lootforge.EnumeratedClasses.TierOfPlay;

public final class RarityRoller {

    private RarityRoller() {}

    public static MagicItemRarity rollRarity(TierOfPlay tier, int d100Roll) {
        int r = d100Roll == 0 ? 100 : d100Roll;
        switch (tier) {
            case TIER_1:
                if (r <= 54) return MagicItemRarity.COMMON;
                if (r <= 91) return MagicItemRarity.UNCOMMON;
                return MagicItemRarity.RARE;
            case TIER_2:
                if (r <= 30) return MagicItemRarity.COMMON;
                if (r <= 81) return MagicItemRarity.UNCOMMON;
                if (r <= 98) return MagicItemRarity.RARE;
                return MagicItemRarity.VERY_RARE;
            case TIER_3:
                if (r <= 11) return MagicItemRarity.COMMON;
                if (r <= 34) return MagicItemRarity.UNCOMMON;
                if (r <= 70) return MagicItemRarity.RARE;
                if (r <= 93) return MagicItemRarity.VERY_RARE;
                return MagicItemRarity.LEGENDARY;
            case TIER_4:
            default:
                if (r <= 20) return MagicItemRarity.RARE;
                if (r <= 64) return MagicItemRarity.VERY_RARE;
                return MagicItemRarity.LEGENDARY;
        }
    }
}
