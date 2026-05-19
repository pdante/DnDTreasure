package com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses;

public enum TierOfPlay {
    TIER_1, TIER_2, TIER_3, TIER_4;

    public ChallengeRating toChallengeRating() {
        switch (this) {
            case TIER_1: return ChallengeRating.ZERO;
            case TIER_2: return ChallengeRating.FIVE;
            case TIER_3: return ChallengeRating.ELEVEN;
            case TIER_4: return ChallengeRating.SEVENTEEN;
            default: return ChallengeRating.ZERO;
        }
    }

    public static TierOfPlay fromChallengeRating(ChallengeRating cr) {
        switch (cr) {
            case ZERO: return TIER_1;
            case FIVE: return TIER_2;
            case ELEVEN: return TIER_3;
            case SEVENTEEN: return TIER_4;
            default: return TIER_1;
        }
    }
}
