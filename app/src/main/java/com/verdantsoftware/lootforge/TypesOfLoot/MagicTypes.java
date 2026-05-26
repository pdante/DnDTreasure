package com.verdantsoftware.lootforge.TypesOfLoot;

import com.verdantsoftware.lootforge.Dice.Dice;
import com.verdantsoftware.lootforge.TreasureCreationClasses.AbstractGeneratedStrings;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateItemStrings;
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
import com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.ValuableItems;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.MagicItemTableObject;

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
        AbstractGeneratedStrings generatedStrings = new GenerateItemStrings();
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
                 generatedStrings.setName("Something went horribly with magic spell generation!");
                magicItemTableObject.generatedStrings = generatedStrings;
                System.out.println(magicItemTableObject.getName());
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
