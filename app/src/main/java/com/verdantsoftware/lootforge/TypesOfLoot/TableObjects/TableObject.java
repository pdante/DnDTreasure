package com.verdantsoftware.lootforge.TypesOfLoot.TableObjects;

import com.verdantsoftware.lootforge.EnumeratedClasses.TypeOfItem;
import com.verdantsoftware.lootforge.TreasureCreationClasses.AbstractGeneratedStrings;

/**
 * Created by PaulD on 2015-12-02.
 */
public abstract class TableObject {
    /**
     * What this entry is. Callers must branch on this rather than on
     * getClass(): the subclasses are structurally identical, and R8 merges
     * them in release builds, so class identity is not reliable there.
     */
    public enum Kind { ART, GEM, MAGIC_ITEM }

    public int numberOfItem;
    public AbstractGeneratedStrings generatedStrings;
    private final Kind kind;

    protected TableObject(Kind kind) {
        this.kind = kind;
    }

    public Kind getKind() {
        return kind;
    }


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
