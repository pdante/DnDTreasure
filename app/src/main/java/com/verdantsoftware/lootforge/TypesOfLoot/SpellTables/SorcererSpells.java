package com.verdantsoftware.lootforge.TypesOfLoot.SpellTables;

/**
 * Created by PaulD on 2015-11-30.
 */
public class SorcererSpells extends AbstractSpells implements Spells {

    public SorcererSpells() {
    }
    protected String[] level0 = {"Acid Splash", "Blade Ward", "Chill Touch", "Dancing Lights", "Fire Bolt", "Friends", "Light", "Mage Hand", "Mending", "Message", "Minor Illusion", "Poison Spray", "Prestidigitation", "Ray of Frost", "Shocking Grasp", "True Strike" };
    protected String[] level1 = {"Burning Hands", "Charm Person", "Chromatic Orb", "Color Spray", "Comprehend", "Detect Magic", "Disguise Self", "Expeditious Retreat", "False Life", "Feather Fali", "Fog Cloud", "Jump", "Mage Armor", "Magic Missile", "Ray of Sickness", "Shield", "Silent Image", "Sleep", "Thunderwave", "Witch Bolt"};
    protected String[] level2 = {"Alter Self", "Blindness/Deafness", "Blur", "Cloud of Daggers", "Crown of Madness", "Darkness", "Darkvision", "Detect Thoughts", "Enhance Ability", "Enlarge/Reduce", "Gust of Wind", "Hold Person", "Invisibility", "Knock", "Levitate", "Mirror Image", "Misty Step", "Phantasmal Force", "Scorching Ray", "See Invisibility", "Shatter", "Spider Climb", "Suggestion", "Web"};
    protected String[] level3 = {"Blink", "Clairvoyance", "Counterspell", "Daylight", "Dispel Magic", "Fear", "Fireball", "Fly", "Gaseous Form", "Haste", "Hypnotic Pattern", "Lightning Bolt", "Major Image", "Protection from Energy", "Sleet Storm", "Slow", "Stinking Cloud", "Tongues", "Water Breathing", "Water Walk"};
    protected String[] level4 = {"Banishment", "Blight", "Confusion", "Dimension Door", "Dominate Beast", "Greater Invisibility", "Ice Storm", "Polymorph", "Stoneskin", "Wall of Fire"};
    protected String[] level5 = {"Animate Objects", "Cloudkill", "Cone of Cold", "Creation", "Dominate Person", "Hold Monster", "Insect Plague", "Seeming", "Telekinesis", "Teleportation Cirele", "Wall of Stone"};
    protected String[] level6 = {"Arcane Gate", "Chain Lightning", "Circle of Death", "Disintegrate", "Eyebite", "Globe of Invulnerability", "Mass Suggestion", "Move Earth", "Sunbeam", "True Seeing"};
    protected String[] level7 = {"Delayed Blast", "Fireball", "Etherealness", "Finger of Death", "Fire Storm", "Plane Shift", "Prismatic Spray", "Reverse Gravity", "Teleport"};
    protected String[] level8 = {"Dominate", "Earthquake", "Incendiary Cloud", "Power Word Stun", "Sunburst"};
    protected String[] level9 = {"Gate Meteor", "Swarm", "Power Word Kill", "Time Stop", "Wish"};

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

