package com.verdantsoftware.lootforge.TypesOfLoot.SpellTables;

/**
 * Created by PaulD on 2015-11-30.
 */
public class PaladinSpells extends AbstractSpells implements Spells {

    public PaladinSpells() {
    }
    protected String[] level0 = {"Error, No Level 0 Paladin spells"};
    protected String[] level1 = {"Bless", "Command", "Compel;ed Duel", "Cure Wounds", "Detect Evil and Good", "Detect Magic", "Detect Poison and Disease", "Divine Favor", "Heroism", "Protect from Evil and Good", "Purify Food and Drink", "Searing Smite", "Shield of Faith", "Thunderous Smite", "Wrathful Smite"};
    protected String[] level2 = {"Aid", "Branding Smite", "Fine Steed", "Lesser Restoration", "Locate Object", "Magic Weapon", "Protect from Poison", "Zone of Truth"};
    protected String[] level3 = {"Aura of Vitality", "Blinding Smite", "Create Food and Water", "Crusader's Mantle", "Daylight", "Dispel Magic", "Elemental Weapon", "Magic Circle", "Remove Curse", "Revivify"};
    protected String[] level4 = {"Aura of Life", "Aura of Purity", "Banishment", "Death Ward", "Locate Creature", "Staggering Smite"};
    protected String[] level5 = {"Banishing Smite", "Circle of Power", "Destructive Smite", "Dispel Evil and Good", "Geas", "Raise Dead"};
    protected String[] level6 = {"Error, No Level 6 Paladin spells"};
    protected String[] level7 = {"Error, No Level 7 Paladin spells"};
    protected String[] level8 = {"Error, No Level 8 Paladin spells"};
    protected String[] level9 = {"Error, No Level 9 Paladin spells"};

    public String getSpell(int level){
        return super.getSpell(level, this);
    }

    @Override
    public String[] getSpells(int level){
        switch(level){
            case 0:
                return level0;
            case 1:
                return level1;
            case 2:
                return level2;
            case 3:
                return level3;
            case 4:
                return level4;
            case 5:
                return level5;
            case 6:
                return level6;
            case 7:
                return level7;
            case 8:
                return level8;
            default:
                return level9;
        }
    }
}

