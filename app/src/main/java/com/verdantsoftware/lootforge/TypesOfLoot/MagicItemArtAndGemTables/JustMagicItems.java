package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables;

import com.verdantsoftware.lootforge.Dice.Dice;
import com.verdantsoftware.lootforge.EnumeratedClasses.ChallengeRating;
import com.verdantsoftware.lootforge.EnumeratedClasses.TypeOfEncounter;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables
        .MagicItemTables.MagicItemTable;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables
        .MagicItemTables.MagicItemTable_A;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables
        .MagicItemTables.MagicItemTable_B;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_C;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_D;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_E;

import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables
        .MagicItemTables.MagicItemTable_Empty;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_F;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_G;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_H;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_I;


import com.verdantsoftware.lootforge.TypesOfLoot.MagicItems;

/**
 * Created by pdante on 9/10/2016.
 */
public class JustMagicItems extends Loot implements MagicItems {
    protected MagicItemTable table;
    private Dice d = new Dice();

    public JustMagicItems(ChallengeRating challengeRating, int d100) {
        super(challengeRating, d100, TypeOfEncounter.HORDE);
    }

    @Override
    public MagicItemTable createStuff() {
        switch (challenge) {
            case ZERO:
                if(d100 <37){
                    d100 = d.roll(100);
                    table = createStuff();
                } else if (d100 < 45) {
                    table = new MagicItemTable_A();
                } else if (d100 < 53) {
                    table = new MagicItemTable_A();

                } else if (d100 < 61) {
                    table = new MagicItemTable_A();

                } else if (d100 < 66) {
                    table = new MagicItemTable_B();

                } else if (d100 < 71) {
                    table = new MagicItemTable_B();

                } else if (d100 < 76) {
                    table = new MagicItemTable_A();

                } else if (d100 < 79) {
                    table = new MagicItemTable_C();

                } else if (d100 < 81) {
                    table = new MagicItemTable_C();

                } else if (d100 < 86) {
                    table = new MagicItemTable_C();

                } else if (d100 < 93) {
                    table = new MagicItemTable_F();

                } else if (d100 < 98) {
                    table = new MagicItemTable_F();

                } else if (d100 < 100) {
                    table = new MagicItemTable_G();

                } else {
                    table = new MagicItemTable_G();

                }
                break;

            case FIVE:
                if(d100 <29){
                    d100 = d.roll(100);
                    table = createStuff();
                } else if (d100 < 33) {
                    table = new MagicItemTable_A();

                } else if (d100 < 37) {
                    table = new MagicItemTable_A();

                } else if (d100 < 41) {
                    table = new MagicItemTable_A();

                } else if (d100 < 45) {
                    table = new MagicItemTable_A();

                } else if (d100 < 50) {
                    table = new MagicItemTable_B();

                } else if (d100 < 55) {
                    table = new MagicItemTable_B();

                } else if (d100 < 60) {
                    table = new MagicItemTable_B();

                } else if (d100 < 64) {
                    table = new MagicItemTable_B();

                } else if (d100 < 67) {
                    table = new MagicItemTable_C();

                } else if (d100 < 70) {
                    table = new MagicItemTable_C();

                } else if (d100 < 73) {
                    table = new MagicItemTable_C();

                } else if (d100 < 75) {
                    table = new MagicItemTable_C();

                } else if (d100 < 77) {
                    table = new MagicItemTable_D();

                } else if (d100 < 79) {
                    table = new MagicItemTable_D();

                } else if (d100 == 79) {
                    table = new MagicItemTable_D();

                } else if (d100 == 80) {
                    table = new MagicItemTable_D();

                } else if (d100 < 85) {
                    table = new MagicItemTable_F();

                } else if (d100 < 89) {
                    table = new MagicItemTable_F();

                } else if (d100 < 92) {
                    table = new MagicItemTable_F();

                } else if (d100 < 95) {
                    table = new MagicItemTable_F();

                } else if (d100 < 97) {
                    table = new MagicItemTable_G();

                } else if (d100 < 99) {
                    table = new MagicItemTable_G();

                } else if (d100 == 99) {
                    table = new MagicItemTable_H();

                } else {
                    table = new MagicItemTable_H();

                }
                break;

            case ELEVEN:
                if(d100 <16){
                    d100 = d.roll(100);
                    table = createStuff();
                } else if (d100 < 20) {
                    int secondary = d.roll(2);
                    if (secondary == 1)
                        table = new MagicItemTable_A();
                    else
                        table = new MagicItemTable_B();

                } else if (d100 < 24) {
                    int secondary = d.roll(2);
                    if (secondary == 1)
                        table = new MagicItemTable_A();
                    else
                        table = new MagicItemTable_B();

                } else if (d100 < 27) {
                    int secondary = d.roll(2);
                    if (secondary == 1)
                        table = new MagicItemTable_A();
                    else
                        table = new MagicItemTable_B();

                } else if (d100 < 30) {
                    int secondary = d.roll(2);
                    if (secondary == 1)
                        table = new MagicItemTable_A();
                    else
                        table = new MagicItemTable_B();

                } else if (d100 < 36) {
                    table = new MagicItemTable_C();

                } else if (d100 < 41) {
                    table = new MagicItemTable_C();

                } else if (d100 < 46) {
                    table = new MagicItemTable_C();

                } else if (d100 < 51) {
                    table = new MagicItemTable_C();

                } else if (d100 < 55) {
                    table = new MagicItemTable_D();

                } else if (d100 < 59) {
                    table = new MagicItemTable_D();

                } else if (d100 < 63) {
                    table = new MagicItemTable_D();

                } else if (d100 < 67) {
                    table = new MagicItemTable_D();

                } else if (d100 < 69) {
                    table = new MagicItemTable_D();

                } else if (d100 < 71) {
                    table = new MagicItemTable_D();

                } else if (d100 < 73) {
                    table = new MagicItemTable_D();

                } else if (d100 < 75) {
                    table = new MagicItemTable_D();

                } else if (d100 < 77) {
                    int secondary = d.roll(2);
                    if (secondary == 1)
                        table = new MagicItemTable_F();
                    else
                        table = new MagicItemTable_G();

                } else if (d100 < 79) {
                    int secondary = d.roll(2);
                    if (secondary == 1)
                        table = new MagicItemTable_F();
                    else
                        table = new MagicItemTable_G();
                } else if (d100 < 81) {
                    int secondary = d.roll(2);
                    if (secondary == 1)
                        table = new MagicItemTable_F();
                    else
                        table = new MagicItemTable_G();
                } else if (d100 < 83) {
                    int secondary = d.roll(2);
                    if (secondary == 1)
                        table = new MagicItemTable_F();
                    else
                        table = new MagicItemTable_G();
                } else if (d100 < 86) {
                    table = new MagicItemTable_H();

                } else if (d100 < 89) {
                    table = new MagicItemTable_H();

                } else if (d100 < 91) {
                    table = new MagicItemTable_H();

                } else if (d100 < 93) {
                    table = new MagicItemTable_H();

                } else if (d100 < 95) {
                    table = new MagicItemTable_I();

                } else if (d100 < 97) {
                    table = new MagicItemTable_I();

                } else if (d100 < 99) {
                    table = new MagicItemTable_I();

                } else {
                    table = new MagicItemTable_I();

                }
                break;

            default: //SEVENTEEN
                if(d100 <3){
                    d100 = d.roll(100);
                    table = createStuff();
                }
                else if (d100 < 6) {
                    table = new MagicItemTable_C();

                } else if (d100 < 9) {
                    table = new MagicItemTable_C();

                } else if (d100 < 12) {
                    table = new MagicItemTable_C();

                } else if (d100 < 15) {
                    table = new MagicItemTable_C();

                } else if (d100 < 23) {
                    table = new MagicItemTable_D();

                } else if (d100 < 31) {
                    table = new MagicItemTable_D();

                } else if (d100 < 39) {
                    table = new MagicItemTable_D();

                } else if (d100 < 47) {
                    table = new MagicItemTable_D();

                } else if (d100 < 53) {
                    table = new MagicItemTable_E();

                } else if (d100 < 59) {
                    table = new MagicItemTable_E();

                } else if (d100 < 64) {
                    table = new MagicItemTable_E();

                } else if (d100 < 69) {
                    table = new MagicItemTable_E();

                } else if (d100 == 69) {
                    table = new MagicItemTable_G();

                } else if (d100 == 70) {
                    table = new MagicItemTable_G();

                } else if (d100 == 71) {
                    table = new MagicItemTable_G();

                } else if (d100 == 72) {
                    table = new MagicItemTable_G();

                } else if (d100 < 75) {
                    table = new MagicItemTable_H();

                } else if (d100 < 77) {
                    table = new MagicItemTable_H();

                } else if (d100 < 78) {
                    table = new MagicItemTable_H();

                } else if (d100 < 81) {
                    table = new MagicItemTable_H();

                } else if (d100 < 86) {
                    table = new MagicItemTable_I();

                } else if (d100 < 91) {
                    table = new MagicItemTable_I();

                } else if (d100 < 96) {
                    table = new MagicItemTable_I();

                } else {
                    table = new MagicItemTable_I();
                }
                break;
        }
        if(!(table instanceof MagicItemTable_Empty))
            generateMagicItems(table);
        return new MagicItemTable_Empty();
    }

    private void generateMagicItems(MagicItemTable table) {
        int random100;

            random100 = d.roll(100);
            list.addToLoot(table.getItem(random100));

    }
}
