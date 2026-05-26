package com.verdantsoftware.lootforge.Dice;

/**
 * Created by PaulD on 2015-11-20.
 */
public interface Roll {
    int roll(int numberOfDice, int dieType);
    int roll(int dieType);
}
