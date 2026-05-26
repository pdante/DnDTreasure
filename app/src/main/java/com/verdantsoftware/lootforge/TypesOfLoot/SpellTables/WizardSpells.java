package com.verdantsoftware.lootforge.TypesOfLoot.SpellTables;

/**
 * Created by PaulD on 2015-11-30.
 */
public class WizardSpells extends AbstractSpells implements Spells {

    public WizardSpells() {
    }

    protected String[] level0 = {"Acid Splash", "Blade Ward", " Chill Touch", " Dancing Lights", " Fire Bolt", " Friends", " Light", " Mage Hand", " Mending", " Message", " Minor Illusion", " Poison Spray", " Prestidigitation", " Ray of Frost", " Shocking Grasp", " True Strike" };
    protected String[] level1 = {"Alarm", " Burning Hands", " Charm Person", " Chromatic Orb", " Color Spray", " Comprehend Languages", " Detect Magic", " Disguise Self", " Expeditious Retreat", " False Life", " Feather Fall", " Find Familiar", " Fog Cloud", " Grease", " Identify", " Illusory Script", " Jump", " Longstrider", " Mage Armor", " Magic Missile", " Protection from Evil and Good", " Ray of Sickness", " Shield", " Silent Image", " Sleep", " Tasha's Hideous Laughter", " Tenser's Floating Disk", " Thunderwave", " Unseen Servant", " Witch Bolt" };
    protected String[] level2 = {"Alter Self", " Arcane Lock", " Blindness/Deafness", " Blur", " Cloud of Daggers", " Continual Flame", " Crown of Madness", " Darkness", " Darkvision", " Detect Thoughts", " Enlarge/Reduce", " Flaming Sphere", " Gentle Repose", " Gust of Wind", "Hold Person", " Invisibility", " Knock", " Levitate", " Locate Object", " Magic Mouth", " Magic Weapon", " Melf's Acid Arrow", " Mirror Image", " Misty Step", " Nystul's Magic Aura", " Phantasmal Force", " Ray of Enfeeblement", " Rope Trick", " Scorching Ray", " See Invisibility", " Shatter", " Spider Climb", " Suggestion", " Web" };
    protected String[] level3 = {"Animate Dead", " Bestow Curse", " Blink", " Clairvoyance", " Counterspell", " Dispel Magic", " Fear", " Feign Death", " Fireball", " Fly", " Gaseous Form", " Glyph of Warding", " Haste", " Hypnotic Pattern", " Leomund's Tiny Hut", " Lightning Bolt", " Magic Circle", " Major Image", " Nondetection", " Phantom Steed", " Protection from Energy", " Remove Curse", " Sending", " Sleet Storm", " Slow", " Stinking Cloud", " Tongues", " Vampiric Touch", " Water Breathing" };
    protected String[] level4 = {"Arcane Eye", " Banishment", " Blight", " Confusion", " Conjure Minor Elementals", " Control Water", " Dimension Door", " Evard's Black Tentacles", " Fabricate", " Fire Shield", " Greater Invisibility", " Hallucinatory Terrain", " Ice Storm", " Leomund's Secret Chest", " Locate Creature", " Mordenkainen's Failhful Hound", " Mordenkainen's protected Sanctum", " Otiluke's Resilient Sphere", " Phantasmal Killer", " Polymorph", " Stone Shape", " Stoneskin", " Wall of Fire" };
    protected String[] level5 = {"Animate Objects", " Bigby's Hand", " Cloudkill", " Cone of Cold", " Conjure Elemenlal", " Contact Other Plane", " Creation", " Dominate Person", " Dream", " Geas", " Hold Monster", " Legend Lore", " Mislead", " Modify Memory", " Passwall", " Planar Binding", " Rary's Telepathic Bond", " Scrying", " Seeming", " Telekinesis", " Teleportation", " Wall of Force", " Wall of Stone" };
    protected String[] level6 = {"Arcane Gate", " Chain Lightning", " Circle of Death", " Contingeney", " Create Undead", " Disintegrate", " Drawmij's Instant Summons", " Eyebite", " Flesh to Stone", " Globe of Invulnerability", " Guards and Wards", " Magic Jar", " Mass Suggestion", " Move Earth", " Otiluke's Freezing Sphere", " Otto's Irresislible Dance", " Programmed Illusion", " Sunbeam", " True Seeing", " Wall of Ice" };
    protected String[] level7 = {"Delayed Blast", " Fireball", " Etherealness", " Finger of Death", " Forcecage", " Mirage Arcane", " Mordenkainen's Magnificent Mansion", " Mordenkainen's Plane Shift", " Prismatic Spray", " Project Image", " Reverse Gravity", " Sequester", " Simulacrum", " Symbol", " Teleport" };
    protected String[] level8 = {"Antimagic Field", " Antipathy/Sympathy", " Clone", " Control Weather", " Demiplane", " Dominate Feeblemind", " Incendiary Cloud", " Maze", " Mind Blank", " Power Word Stun", " Sunburst", " Telepathy", " Trap the Soul" };
    protected String[] level9 = {"Astral Projection", " Foresight", " Gale", " Imprisonment", " Meteor Swarm", " Power Word Kill", " Prismatic Wall", " Shapechange", " Time Stop", " True Polymorph", " Weird", " Wish" };

    public String getSpell(int level) {
        return super.getSpell(level, this);
    }

    @Override
    public String[] getSpells(int level) {
        switch (level) {
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

