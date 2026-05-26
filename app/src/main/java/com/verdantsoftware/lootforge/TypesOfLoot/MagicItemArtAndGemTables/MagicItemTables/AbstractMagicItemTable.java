package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables;

import com.verdantsoftware.lootforge.Dice.Dice;
import com.verdantsoftware.lootforge.TreasureCreationClasses.AbstractGeneratedStrings;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateSpell;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.MagicItemTableObject;

/**
 * Created by PaulD on 2015-12-03.
 */
public abstract class AbstractMagicItemTable extends GenerateSpell {
    protected Dice d = new Dice();
    protected int secondary;
    MagicItemTableObject magicItemTableObject = new MagicItemTableObject();
    protected  GenerateSpell spells;
    protected AbstractGeneratedStrings generatedStrings;

    protected MagicItemTableObject generateItemString(MagicItemTableObject magicItemTableObject){
        AbstractGeneratedStrings generatedStrings = magicItemTableObject.generatedStrings;
        String item = generatedStrings.getName();
        String table = generatedStrings.getMagicItemtable();
        if (item.length() <= 21)
            generatedStrings.setName(item + " " + table);
        else if (item.length() <31)
            generatedStrings.setName(item + "\r\n   " + table);
        else
            generatedStrings.setName(getRow(item, table));
        return magicItemTableObject;
    }

    private String getRow(String name, String table){
        if (name.length() <= 18)
            return name + " " + table;
        else if (name.length() <28)
            return name + "\r\n   " + table;
        else {
            char letter = name.charAt(30);
            int index = 30;
            while (letter != ' '){
                index--;
                letter = name.charAt(index);
            }
            return name.substring(0,index-1) + "\r\n   " + getRow(name.substring(index+1),table);
    }
}
}
