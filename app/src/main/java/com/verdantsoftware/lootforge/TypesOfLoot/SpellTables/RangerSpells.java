package com.verdantsoftware.lootforge.TypesOfLoot.SpellTables;

/**
 * Created by PaulD on 2015-11-30.
 */
public class RangerSpells extends AbstractSpells implements Spells {

    public RangerSpells() {
    }
    protected String[] level0 = {"Error, Rangers have no cantrips"};
    protected String[] level1 = {"Alarm", "Animal Friendship", "Cure Wounds", "Detect Magic", "Detect Poison and Disease", "Ensnaring Strike", "Fog Cloud", "Goodberry", "Hail of Thorns", "Hunter's Mark", "Jump", "Longstrider", "Speak with Animals"};
    protected String[] level2 = {"Animal Messenger", "Barkskin", "Beast Sense", "Cordon of Arrows", "Darkvision", "Find Traps", "Lesser Restoration", "Locate Animals or Plants", "Locate Object", "Pass without Trace", "Protection from Poison", "Silence", "Spike Growth"};
    protected String[] level3 = {"Conjure Animals", "Conjure Barrage", "Daylight", "Lightning Arrow", "Nondetection", "Plant Growth", "Protection from Energy", "Speak with Plants", "Water Breathing", "Water Walk", "Wind Wall"};
    protected String[] level4 = {"Conjure Woodland Beings", "Freedom of Movement", "Grasping Vine", "Locate Creature", "Stoneskin"};
    protected String[] level5 = {"Commune with Nature", "Conjure Volley", "Swift Quiver", "Tree Stride"};
    protected String[] level6 = {"Error, Rangers have no level 6 spells"};
    protected String[] level7 = {"Error, Rangers have no level 7 spells"};
    protected String[] level8 = {"Error, Rangers have no level 8 spells"};
    protected String[] level9 = {"Error, Rangers have no level 9 spells"};

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

