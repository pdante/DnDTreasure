package com.verdantsoftware.lootforge.TypesOfLoot.SpellTables;

/**
 * Created by PaulD on 2015-11-30.
 */
public class ClericSpells extends AbstractSpells implements Spells {

    public ClericSpells() {
    }
    protected String[] level0 = {"Guidence", "Light", "Mending", "Resistance", "Sacred Flame", "Spare the Dying", "Thaumaturgy"};
    protected String[] level1 = {"Bane", "Bless", "Command", "Create or Destroy Water", "Cure Wounds", "Detect Evil", "Detect Magic", "Detect Poison and Disease", "Guiding Bolt", "Healing Word", "Inflict Wounds", "Protect from Evil and Good", "Purify Food and Drink", "Sanctuary", "Shield of Faith"};
    protected String[] level2 = {"Aid", "Augury", "Blindness/Deafness", "Calm Emotions", "Continual Flame", "Enhance Ability", "Find Traps", "Gentle Repose", "Hold Person", "Lesser Restoration", "Locate Object", " Prayer of Healing", "Protection from Poison", "Silence", "Spiritual Weapon", "Warding Bond", "Zone of Truth"};
    protected String[] level3 = {"Animate Dead", "Beacon of Hope", "Bestow Curse", "Clairvoyance", "Create Food and Water", "Daylight", "Dispel Magic", "Feign Death", "Glyph of Warding", "Magic Circle", "Mass Healing Word", "Meld into Stone", "Protection from Energy", "Remove Curse", "Revivify", "Sending", "Speak with Dead", "Spirit Guardian", "Tongues", "Water Walk"};
    protected String[] level4 = {"Banishment", "Control Water", "Death Ward", "Divination", "Freedom of Movement", "Guardian of Faith", "Locate Creature", "Stone Shape"};
    protected String[] level5 = {"Commune", "Contagion", "Dispel Evil and Good", "Flame Strike", "Geas", "Greater Restoration", "Hallow", "Insect Plague", "Legend Lore", "Mass Cure Wounds", "Planar Binding", "Raise Dead", "Scrying"};
    protected String[] level6 = {"Blade Barrier", "Create Undead", "Find the Path", "Forbiddance", "Harm", "Heal", "Heroes' Feast", "Planar Ally", "True Seeing", "Word of Recall"};
    protected String[] level7 = {"Conjure Celestial", "Divine Word", "Etherealness", "Fire Storm", "Plane Shift", "Regenerate", "Resurrection", "Symbol"};
    protected String[] level8 = {"Antimagic Field", "Control Weather", "Earthquake", "Holy Aura"};
    protected String[] level9 = {"Astral Projection", "Gate", "Mass Heal", "True Resurrection"};

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

