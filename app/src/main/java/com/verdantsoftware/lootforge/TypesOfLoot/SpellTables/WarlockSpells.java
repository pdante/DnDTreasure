package com.verdantsoftware.lootforge.TypesOfLoot.SpellTables;

/**
 * Created by PaulD on 2015-11-30.
 */
public class WarlockSpells extends AbstractSpells implements Spells {

    public WarlockSpells() {
    }
    protected String[] level0 = {"Blade Ward", "Chill Touch", "Eldritch Blast", "Friends", "Mage Hand", "Minor Illusion", "Poison Spray", "Prestidigitation True Strike"};
    protected String[] level1 = {"Armor of Agathys", "Arms of Hadar", "Charm Person", "Comprehend Languages", "Expeditious Retreat", "Hellish Rebuke", "Hex", "Illusory Script", "Protection from Evil and Good", "Unseen Servant", "Witch Bolt"};
    protected String[] level2 = {"Cloud of Daggers", "Crown of Madness", "Darkness", "Enthrall", "Hold Person", "Invisibility", "Mirror Image", "Misty Step", "Ray of Enfeeblement", "Shatter", "Spider Climb", "Suggestion"};
    protected String[] level3 = {"Counterspell", "Dispel Magic", "Fear", "Fly", "Gaseous Form", "Hunger of Hadar", "Hypnotic Pattern", "Magic Circle", "Major Image", "Remove Curse", "Tongues", "Vampiric Touch"};
    protected String[] level4 = {"Banishment", "Blight", "Dimension Door", "Hallucinatory Terrain"};
    protected String[] level5 = {"Contact Other Plane", " Dream", " Hold Monster", " Scrying"};
    protected String[] level6 = {"Arcane Gate", "Circle of Death", "Conjure Fey", "Create Undead", "Eyebite", "Flesh to Stone", "Mass Suggestion", "True Seeing"};
    protected String[] level7 = {"Etherealness", "Finger of Death", "Forcecage", "Plane Shift"};
    protected String[] level8 = {"Demiplane", "Dominate Monster", "Feeblemind", "Glibness", "Power Word Stun"};
    protected String[] level9 = {"Astral Projection", "Foresight", "Imprisonment", "Power Word Kill", "True Polymorph"};

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

