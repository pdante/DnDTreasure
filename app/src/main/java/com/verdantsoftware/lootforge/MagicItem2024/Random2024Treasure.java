package com.verdantsoftware.lootforge.MagicItem2024;

import com.verdantsoftware.lootforge.Dice.Dice;
import com.verdantsoftware.lootforge.EnumeratedClasses.ChallengeRating;

public final class Random2024Treasure {

    public enum Currency { GP, PP }

    public static class IndividualResult {
        public final Currency currency;
        public final int amount;
        public IndividualResult(Currency currency, int amount) {
            this.currency = currency;
            this.amount = amount;
        }
    }

    public static class HoardResult {
        public final Currency currency;
        public final int amount;
        public final int magicItemCount;
        public HoardResult(Currency currency, int amount, int magicItemCount) {
            this.currency = currency;
            this.amount = amount;
            this.magicItemCount = magicItemCount;
        }
    }

    private Random2024Treasure() {}

    public static IndividualResult rollIndividual(ChallengeRating cr) {
        Dice d = new Dice();
        switch (cr) {
            case ZERO:
                return new IndividualResult(Currency.GP, d.roll(3, 6));
            case FIVE:
                return new IndividualResult(Currency.GP, d.roll(2, 8) * 10);
            case ELEVEN:
                return new IndividualResult(Currency.PP, d.roll(2, 10) * 10);
            case SEVENTEEN:
            default:
                return new IndividualResult(Currency.PP, d.roll(2, 8) * 100);
        }
    }

    public static HoardResult rollHoard(ChallengeRating cr) {
        Dice d = new Dice();
        switch (cr) {
            case ZERO:
                return new HoardResult(Currency.GP, d.roll(2, 4) * 100, Math.max(0, d.roll(1, 4) - 1));
            case FIVE:
                return new HoardResult(Currency.GP, d.roll(8, 10) * 100, d.roll(1, 3));
            case ELEVEN:
                return new HoardResult(Currency.GP, d.roll(8, 8) * 1000, d.roll(1, 4));
            case SEVENTEEN:
            default:
                return new HoardResult(Currency.GP, d.roll(6, 10) * 10000, d.roll(1, 6));
        }
    }
}
