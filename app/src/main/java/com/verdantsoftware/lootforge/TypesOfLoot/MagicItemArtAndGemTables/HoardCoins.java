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
public class HoardCoins extends Loot implements Coins {

    public HoardCoins(ChallengeRating challengeRating, int d100) {
        super(challengeRating, d100, TypeOfEncounter.HORDE);
    }
    @Override
    public void createStuff() {
        int numberOfCoins;
        switch (challenge) {
            case ZERO:
                numberOfCoins = d.roll(6, 6)*100;
                list.addToCoins(TypesOfCoins.COPPER, numberOfCoins);
                numberOfCoins = d.roll(3, 6)*100;
                list.addToCoins(TypesOfCoins.SILVER, numberOfCoins);
                numberOfCoins = d.roll(2, 6)*10;
                list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                break;
            case FIVE:
                numberOfCoins = d.roll(2, 6)*100;
                list.addToCoins(TypesOfCoins.COPPER, numberOfCoins);
                numberOfCoins = d.roll(2, 6)*1000;
                list.addToCoins(TypesOfCoins.SILVER, numberOfCoins);
                numberOfCoins = d.roll(6, 6)*100;
                list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                numberOfCoins = d.roll(3, 6)*10;
                list.addToCoins(TypesOfCoins.PLATINUM, numberOfCoins);
                break;
            case ELEVEN:
                numberOfCoins = d.roll(4, 6)*1000;
                list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                numberOfCoins = d.roll(5, 6)*100;
                list.addToCoins(TypesOfCoins.PLATINUM, numberOfCoins);
                break;
            case SEVENTEEN:
                numberOfCoins = d.roll(12, 6)*1000;
                list.addToCoins(TypesOfCoins.GOLD, numberOfCoins);
                numberOfCoins = d.roll(8, 6)*1000;
                list.addToCoins(TypesOfCoins.PLATINUM, numberOfCoins);
                break;
        }
    }

}
