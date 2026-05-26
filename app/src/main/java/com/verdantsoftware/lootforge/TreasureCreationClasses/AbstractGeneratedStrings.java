package com.verdantsoftware.lootforge.TreasureCreationClasses;

import com.verdantsoftware.lootforge.EnumeratedClasses.TypeOfItem;

/**
 * Created by PaulD on 2015-12-04.
 */
public abstract class AbstractGeneratedStrings {
    protected String level = "";
    protected String magicItemtable = "";
    protected String name = "";
    protected TypeOfItem typeOfItem;
    protected String spellCLass = "";

    public TypeOfItem getTypeOfItem() {
        return typeOfItem;
    }

    public void setTypeOfItem(TypeOfItem typeOfItem) {
        this.typeOfItem = typeOfItem;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMagicItemtable() {
        return magicItemtable;
    }

    public void setMagicItemtable(String magicItemtable) {
        this.magicItemtable = magicItemtable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpellCLass(){
        return spellCLass;
    }

    public void setSpellCLass(String spellCLass) {
        this.spellCLass = spellCLass;
    }
}
