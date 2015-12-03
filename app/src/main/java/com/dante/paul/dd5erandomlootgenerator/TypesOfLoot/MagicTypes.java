package com.dante.paul.dd5erandomlootgenerator.TypesOfLoot;

import com.dante.paul.dd5erandomlootgenerator.Dice.Dice;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.MagicItemTables.MagicItemTable;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.MagicItemTables.MagicItemTable_A;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.MagicItemTables.MagicItemTable_B;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.MagicItemTables.MagicItemTable_C;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.MagicItemTables.MagicItemTable_D;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.MagicItemTables.MagicItemTable_E;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.MagicItemTables.MagicItemTable_F;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.MagicItemTables.MagicItemTable_G;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.MagicItemTables.MagicItemTable_H;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.MagicItemTables.MagicItemTable_I;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.TableObjects.MagicItemTableObject;

/**
 * Created by PaulD on 2015-11-23.
 */
public class MagicTypes extends ValuableItems {
    Dice d = new Dice();
    int number;
    MagicItemTableObject magicItemTableObject = new MagicItemTableObject();
    MagicItemTable magicItemTable;


    //TODO randomly generate spells of given level
    public MagicItemTableObject getItem(String table) {
        number = d.roll(100);
        switch (table) {
            case "A":
                magicItemTable = new MagicItemTable_A();
                break;
            case "B":
                magicItemTable = new MagicItemTable_B();
                break;
            case "C":
                magicItemTable = new MagicItemTable_C();
                break;
            case "D":
                magicItemTable = new MagicItemTable_D();
                break;
            case "E":
                magicItemTable = new MagicItemTable_E();
                break;
            //TODO create other magic item tables
            case "F":
                magicItemTable = new MagicItemTable_F();
                break;
            case "G":
                magicItemTable = new MagicItemTable_G();
                break;
            case "H":
                magicItemTable = new MagicItemTable_H();
                break;
            case "I":
                magicItemTable = new MagicItemTable_I();
            default:
                magicItemTableObject.itemName = "Something is fucked with magic item generation!";
                System.out.println(magicItemTableObject.itemName);
        }
        magicItemTableObject = magicItemTable.getItem(number);
        return magicItemTableObject;
    }

    @Override
    public MagicItemTableObject getItem(int value) {
        magicItemTableObject = magicItemTable.getItem(value);
        return magicItemTableObject;
    }
}
