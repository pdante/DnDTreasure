package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables;

import com.verdantsoftware.lootforge.EnumeratedClasses.ChallengeRating;
import com.verdantsoftware.lootforge.EnumeratedClasses.TypeOfEncounter;
import com.verdantsoftware.lootforge.EnumeratedClasses.TypesOfCoins;
import com.verdantsoftware.lootforge.TypesOfLoot.Coins;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables
        .MagicItemTables.MagicItemTable;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables
        .MagicItemTables.MagicItemTable_A;

/**
 * Created by PaulD on 2015-11-23.
 */
public class IndividualCoins extends Loot implements Coins {

    public IndividualCoins(ChallengeRating challengeRating, int d100) {
        super(challengeRating, d100, TypeOfEncounter.INDIVIDUAL);
    }

    @Override
    public void createStuff() {
        int numberOfCoins;
        switch (challenge) {
            case ZERO:
                if (d100 < 31) {
                    numberOfCoins = d.roll(5, 6);
                    list.addToCoins(TypesOfCoins.COPPER, numberOfCoins);
                } else if (d100 < 61) {
                    numberOfCoins = d.roll(4, 6);
                    list.addToCoins(TypesOfCoins.SILVER, numberOfCoins);
                } else if (d100 < 71) {
                    numberOfCoins = d.roll(3, 6);
                    list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                } else if (d100 < 96) {
                    numberOfCoins = d.roll(3, 6);
                    list.addToCoins(TypesOfCoins.ELECTUM, numberOfCoins);
                } else {
                    numberOfCoins = d.roll(1, 6);
                    list.addToCoins(TypesOfCoins.PLATINUM, numberOfCoins);
                }
                break;
            case FIVE:
                if (d100 < 31) {
                    numberOfCoins = d.roll(4, 6) * 100;
                    list.addToCoins(TypesOfCoins.COPPER, numberOfCoins);
                    numberOfCoins = d.roll(1, 6) * 10;
                    list.addToCoins(TypesOfCoins.ELECTUM, numberOfCoins);
                } else if (d100 < 61) {
                    numberOfCoins = d.roll(6, 6) * 10;
                    list.addToCoins(TypesOfCoins.SILVER, numberOfCoins);
                    numberOfCoins = d.roll(2, 6) * 10;
                    list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                } else if (d100 < 71) {
                    numberOfCoins = d.roll(3, 6) * 10;
                    list.addToCoins(TypesOfCoins.ELECTUM, numberOfCoins);
                    numberOfCoins = d.roll(2, 6) * 10;
                    list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                } else if (d100 < 96) {
                    numberOfCoins = d.roll(4, 6) * 10;
                    list.addToCoins(TypesOfCoins.ELECTUM, numberOfCoins);
                } else {
                    numberOfCoins = d.roll(2, 6) * 10;
                    list.addToCoins(TypesOfCoins.ELECTUM, numberOfCoins);
                    numberOfCoins = d.roll(3, 6);
                    list.addToCoins(TypesOfCoins.PLATINUM, numberOfCoins);
                }
                break;

            case ELEVEN:
                if (d100 < 21) {
                    numberOfCoins = d.roll(4, 6) * 100;
                    list.addToCoins(TypesOfCoins.SILVER, numberOfCoins);
                    numberOfCoins = d.roll(1, 6) * 100;
                    list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                } else if (d100 < 36) {
                    numberOfCoins = d.roll(1, 6) * 100;
                    list.addToCoins(TypesOfCoins.ELECTUM, numberOfCoins);
                    numberOfCoins = d.roll(1, 6) * 100;
                    list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                } else if (d100 < 76) {
                    numberOfCoins = d.roll(2, 6) * 100;
                    list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                    numberOfCoins = d.roll(1, 6) * 10;
                    list.addToCoins(TypesOfCoins.PLATINUM, numberOfCoins);
                } else {
                    numberOfCoins = d.roll(2, 6) * 100;
                    list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                    numberOfCoins = d.roll(2, 6) * 10;
                    list.addToCoins(TypesOfCoins.PLATINUM, numberOfCoins);
                }
                break;

            case SEVENTEEN:
                if (d100 < 16) {
                    numberOfCoins = d.roll(2, 6) * 1000;
                    list.addToCoins(TypesOfCoins.ELECTUM, numberOfCoins);
                    numberOfCoins = d.roll(8, 6) * 100;
                    list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                } else if (d100 < 56) {
                    numberOfCoins = d.roll(1, 6) * 1000;
                    list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                    numberOfCoins = d.roll(1, 6) * 100;
                    list.addToCoins(TypesOfCoins.PLATINUM, numberOfCoins);
                } else {
                    numberOfCoins = d.roll(1, 6) * 1000;
                    list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                    numberOfCoins = d.roll(2, 6) * 100;
                    list.addToCoins(TypesOfCoins.PLATINUM, numberOfCoins);
                }
                break;

        }
    }

}
