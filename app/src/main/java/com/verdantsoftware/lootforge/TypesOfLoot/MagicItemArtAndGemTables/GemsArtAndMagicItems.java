package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables;

import com.verdantsoftware.lootforge.Dice.Dice;
import com.verdantsoftware.lootforge.EnumeratedClasses.ChallengeRating;
import com.verdantsoftware.lootforge.EnumeratedClasses.TypeOfEncounter;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_A;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_B;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_C;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_D;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_E;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_F;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_G;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_H;
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables.MagicItemTable_I;


import com.verdantsoftware.lootforge.TypesOfLoot.MagicItems;

/**
 * Created by PaulD on 2015-11-20.
 */
public class GemsArtAndMagicItems extends Loot implements MagicItems {
    private int numberOfItems;
    private GemTypes g = new GemTypes();
    private ArtTypes a = new ArtTypes();
    private MagicItemTable table;
    private Dice d = new Dice();

    public GemsArtAndMagicItems(ChallengeRating challengeRating, int d100) {
        super(challengeRating, d100, TypeOfEncounter.HORDE);
    }

    @Override
    public MagicItemTable createStuff() {
        switch (challenge) {
            case ZERO:
                if (d100 < 7) {//no gems or art
                } else if (d100 < 17) {
                    numberOfItems = d.roll(2, 6);
                    generateGemsOrArt(numberOfItems, 10, g);
                } else if (d100 < 27) {
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 25, a);
                } else if (d100 < 37) {
                    numberOfItems = d.roll(2, 6);
                    generateGemsOrArt(numberOfItems, 50, g);
                } else if (d100 < 45) {
                    numberOfItems = d.roll(2, 6);
                    generateGemsOrArt(numberOfItems, 10, g);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_A();
                    generateMagicItems(numberOfItems, table);
                } else if (d100 < 53) {
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 25, a);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_A();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <61){
                    numberOfItems = d.roll(2, 6);
                    generateGemsOrArt(numberOfItems, 50, g);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_A();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <66){
                    numberOfItems = d.roll(2, 6);
                    generateGemsOrArt(numberOfItems, 10, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_B();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <71){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 25, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_B();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <76){
                    numberOfItems = d.roll(2, 6);
                    generateGemsOrArt(numberOfItems, 50, g);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_A();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 79){
                    numberOfItems = d.roll(2, 6);
                    generateGemsOrArt(numberOfItems, 10, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <81){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 25, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 86){
                    numberOfItems = d.roll(2, 6);
                    generateGemsOrArt(numberOfItems, 50, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 93){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 25, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_F();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 98){
                    numberOfItems = d.roll(2, 6);
                    generateGemsOrArt(numberOfItems, 50, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_F();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 100){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 25, a);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_G();
                    generateMagicItems(1, table);
                }else if (d100 == 100){
                    numberOfItems = d.roll(2, 6);
                    generateGemsOrArt(numberOfItems, 50, g);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_G();
                    generateMagicItems(numberOfItems, table);
                } else {
                    numberOfItems = d.roll(2, 6);
                    generateGemsOrArt(numberOfItems, 10, g);
                }
                break;

            case FIVE:
                if (d100 < 4) {//no gems or art
                } else if (d100 < 11) {
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 25, a);
                } else if (d100 < 17) {
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 50, g);
                } else if (d100 < 23) {
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 100, g);
                } else if (d100 < 29) {
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 6);
                } else if (d100 < 33) {
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 25, a);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_A();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <37){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 50, g);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_A();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <41){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 100, g);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_A();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <45){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_A();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <50){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 25, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_B();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 55){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 50, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_B();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <60){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 100, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_B();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 64){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_B();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 67){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 25, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 70){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 50, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 73){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 100, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 75){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 77){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 25, a);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 79){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 50, g);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 == 79){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 100, g);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 == 80){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 85){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 25, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_F();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 89){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 50, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_F();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 92){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 100, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_F();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 95){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_F();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 97){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 100, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_G();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 99){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_G();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 == 99){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 100, g);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_H();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 == 100){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_H();
                    generateMagicItems(numberOfItems, table);
                } else {
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 25, a);
                }
                break;
            case ELEVEN:
                if (d100 < 3) {//no gems or art
                } else if (d100 < 7) {
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                } else if (d100 < 10) {
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 750, a);
                } else if (d100 < 13) {
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 500, g);
                } else if (d100 < 16) {
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 6);
                } else if (d100 < 20) {
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_A();
                    generateMagicItems(numberOfItems, table);
                    numberOfItems = d.roll(1,6);
                    table = new MagicItemTable_B();
                    generateMagicItems(numberOfItems,table);
                }else if (d100 <24){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 750, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_A();
                    generateMagicItems(numberOfItems, table);
                    numberOfItems = d.roll(1,6);
                    table = new MagicItemTable_B();
                    generateMagicItems(numberOfItems,table);
                }else if (d100 <27){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 500, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_A();
                    generateMagicItems(numberOfItems, table);
                    numberOfItems = d.roll(1,6);
                    table = new MagicItemTable_B();
                    generateMagicItems(numberOfItems,table);
                }else if (d100 <30){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_A();
                    generateMagicItems(numberOfItems, table);
                    numberOfItems = d.roll(1,6);
                    table = new MagicItemTable_B();
                    generateMagicItems(numberOfItems,table);
                }else if (d100 <36){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 41){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 750, a);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <46){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 500, g);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 51){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 55){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 59){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 750, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 63){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 500, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 67){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 69){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 71){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 750, a);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 73){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 500, g);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 75){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 77){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_F();
                    generateMagicItems(numberOfItems, table);
                    numberOfItems = d.roll(1,4);
                    table = new MagicItemTable_G();
                    generateMagicItems(numberOfItems,table);
                }else if (d100 < 79){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 750, a);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_F();
                    generateMagicItems(numberOfItems, table);
                    numberOfItems = d.roll(1,4);
                    table = new MagicItemTable_G();
                    generateMagicItems(numberOfItems,table);
                }else if (d100 < 81){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 500, g);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_F();
                    generateMagicItems(numberOfItems, table);
                    numberOfItems = d.roll(1,4);
                    table = new MagicItemTable_G();
                    generateMagicItems(numberOfItems,table);
                }else if (d100 < 83){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_F();
                    generateMagicItems(numberOfItems, table);
                    numberOfItems = d.roll(1,4);
                    table = new MagicItemTable_G();
                    generateMagicItems(numberOfItems,table);
                }else if (d100 < 86){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_H();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 89){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 750, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_H();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 91){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 500, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_H();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 93){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_H();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 95){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_I();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 97){
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 750, a);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_I();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 99){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 500, g);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_I();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 == 100){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 1);
                    table = new MagicItemTable_I();
                    generateMagicItems(numberOfItems, table);
                }else {
                    numberOfItems = d.roll(2, 4);
                    generateGemsOrArt(numberOfItems, 250, a);
                }
                break;

            default: //SEVENTEEN
                if (d100 < 2) {//no gems or art
                } else if (d100 <6) {
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 8);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                } else if (d100 < 9) {
                    numberOfItems = d.roll(1, 10);
                    generateGemsOrArt(numberOfItems, 2500, a);
                    numberOfItems = d.roll(1, 8);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                } else if (d100 < 12) {
                    numberOfItems = d.roll(1, 4);
                    generateGemsOrArt(numberOfItems, 7500, a);
                    numberOfItems = d.roll(1, 8);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                } else if (d100 < 15) {
                    numberOfItems = d.roll(1, 8);
                    generateGemsOrArt(numberOfItems, 5000, g);
                    numberOfItems = d.roll(1, 8);
                    table = new MagicItemTable_C();
                    generateMagicItems(numberOfItems, table);
                } else if (d100 < 23) {
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <31){
                    numberOfItems = d.roll(1, 10);
                    generateGemsOrArt(numberOfItems, 2500, a);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <39){
                    numberOfItems = d.roll(1, 4);
                    generateGemsOrArt(numberOfItems, 7500, a);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <47){
                    numberOfItems = d.roll(1, 8);
                    generateGemsOrArt(numberOfItems, 5000, g);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_D();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <53){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_E();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 59){
                    numberOfItems = d.roll(1, 10);
                    generateGemsOrArt(numberOfItems, 2500, a);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_E();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <64){
                    numberOfItems = d.roll(1, 4);
                    generateGemsOrArt(numberOfItems, 7500, a);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_E();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 < 69){
                    numberOfItems = d.roll(1, 8);
                    generateGemsOrArt(numberOfItems, 5000, g);
                    numberOfItems = d.roll(1, 6);
                    table = new MagicItemTable_E();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 == 69){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_G();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 == 70){
                    numberOfItems = d.roll(1, 10);
                    generateGemsOrArt(numberOfItems, 2500, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_G();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 == 71){
                    numberOfItems = d.roll(1, 4);
                    generateGemsOrArt(numberOfItems, 75000, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_G();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 == 72){
                    numberOfItems = d.roll(1, 8);
                    generateGemsOrArt(numberOfItems, 5000, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_G();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <75){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_H();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <77){
                    numberOfItems = d.roll(1, 10);
                    generateGemsOrArt(numberOfItems, 2500, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_H();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <78){
                    numberOfItems = d.roll(1, 4);
                    generateGemsOrArt(numberOfItems, 7500, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_H();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <81){
                    numberOfItems = d.roll(1, 8);
                    generateGemsOrArt(numberOfItems, 5000, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_H();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <86){
                    numberOfItems = d.roll(3, 6);
                    generateGemsOrArt(numberOfItems, 1000, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_I();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <91){
                    numberOfItems = d.roll(1, 10);
                    generateGemsOrArt(numberOfItems, 2500, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_I();
                    generateMagicItems(numberOfItems, table);
                }else if (d100 <96){
                    numberOfItems = d.roll(1, 4);
                    generateGemsOrArt(numberOfItems, 7500, a);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_I();
                    generateMagicItems(numberOfItems, table);
                }else{
                    numberOfItems = d.roll(1, 8);
                    generateGemsOrArt(numberOfItems, 5000, g);
                    numberOfItems = d.roll(1, 4);
                    table = new MagicItemTable_I();
                    generateMagicItems(numberOfItems, table);
                }
                break;
        }
        return table;
    }

    private void generateGemsOrArt(int numberOfItems, int value, ValuableItems vI) {
        for (int counter = 0; counter < numberOfItems; counter++) {
            list.addToLoot(vI.getItem(value));
        }
    }

    private void generateMagicItems(int numberOfItems, MagicItemTable table) {
        int random100;
        for (int counter = 0; counter < numberOfItems; counter++) {
            random100 = d.roll(100);
            list.addToLoot(table.getItem(random100));
        }
    }

}
