package com.verdantsoftware.lootforge.Dice;

import java.util.Random;

/**
 * Created by PaulD on 2015-11-20.
 */
public class Dice implements Roll {
    public Dice() {
    }

    @Override
    public int roll(int numberOfDice, int dieType) {
        int score=0;
        for (int counter=1;counter<=numberOfDice;counter++){
            score += random(dieType);
        }
        return score;
    }

    @Override
    public int roll(int dieType) {
        return random(dieType);
    }

    private int random(int dieType) {
        Random rand = new Random();
        int roll = rand.nextInt(dieType) + 1;
            return roll;
    }
}
