package com.verdantsoftware.lootforge.TypesOfLoot.TableObjects;

import com.verdantsoftware.lootforge.EnumeratedClasses.TypeOfItem;
import com.verdantsoftware.lootforge.TreasureCreationClasses.AbstractGeneratedStrings;

/**
 * Created by PaulD on 2015-12-02.
 */
public abstract class TableObject {
    public int numberOfItem;
    public AbstractGeneratedStrings generatedStrings;


    public String getName(){
        return generatedStrings.getName();
    }

    public TypeOfItem getItemType(){
        return generatedStrings.getTypeOfItem();
    }

    public void setItemType(TypeOfItem typeOfItem){
        generatedStrings.setTypeOfItem(typeOfItem);
    }
    public void setName(String name){
        generatedStrings.setName(name);
    }

    public String getLevel(){
        return generatedStrings.getLevel();
    }

    public String getSpellClass(){
        return generatedStrings.getSpellCLass();
    }

    public String getItemTable(){
        return generatedStrings.getMagicItemtable();
    }
}
