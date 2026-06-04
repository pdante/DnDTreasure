package com.verdantsoftware.lootforge.TypesOfLoot.SpellTables;

/**
 * Artificer spell list (Eberron: Rising from the Last War /
 * Tasha's Cauldron of Everything).
 *
 * Artificer is a half-caster — they get cantrips (level 0) but
 * no spells above level 5.
 */
public class ArtificerSpells extends AbstractSpells implements Spells {

    protected String[] level0 = {"Acid Splash", "Booming Blade", "Create Bonfire", "Dancing Lights", "Fire Bolt", "Frostbite", "Green-Flame Blade", "Guidance", "Light", "Mage Hand", "Magic Stone", "Mending", "Message", "Poison Spray", "Prestidigitation", "Ray of Frost", "Resistance", "Shocking Grasp", "Spare the Dying", "Sword Burst", "Thorn Whip", "Thunderclap"};
    protected String[] level1 = {"Absorb Elements", "Alarm", "Catapult", "Cure Wounds", "Detect Magic", "Disguise Self", "Expeditious Retreat", "Faerie Fire", "False Life", "Feather Fall", "Grease", "Identify", "Jump", "Longstrider", "Purify Food and Drink", "Sanctuary", "Snare", "Tasha's Caustic Brew"};
    protected String[] level2 = {"Aid", "Alter Self", "Arcane Lock", "Blur", "Continual Flame", "Darkvision", "Enhance Ability", "Enlarge/Reduce", "Heat Metal", "Invisibility", "Lesser Restoration", "Levitate", "Magic Mouth", "Magic Weapon", "Protection from Poison", "Pyrotechnics", "Rope Trick", "See Invisibility", "Skywrite", "Spider Climb", "Web"};
    protected String[] level3 = {"Blink", "Catnap", "Create Food and Water", "Dispel Magic", "Elemental Weapon", "Flame Arrows", "Fly", "Glyph of Warding", "Haste", "Intellect Fortress", "Protection from Energy", "Revivify", "Tiny Servant", "Water Breathing", "Water Walk"};
    protected String[] level4 = {"Arcane Eye", "Elemental Bane", "Fabricate", "Freedom of Movement", "Leomund's Secret Chest", "Mordenkainen's Faithful Hound", "Mordenkainen's Private Sanctum", "Otiluke's Resilient Sphere", "Stone Shape", "Stoneskin", "Summon Construct"};
    protected String[] level5 = {"Animate Objects", "Bigby's Hand", "Creation", "Greater Restoration", "Skill Empowerment", "Transmute Rock", "Wall of Stone"};
    protected String[] level6 = {"Error, Artificers have no level 6 spells"};
    protected String[] level7 = {"Error, Artificers have no level 7 spells"};
    protected String[] level8 = {"Error, Artificers have no level 8 spells"};
    protected String[] level9 = {"Error, Artificers have no level 9 spells"};

    public ArtificerSpells() {
    }

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
