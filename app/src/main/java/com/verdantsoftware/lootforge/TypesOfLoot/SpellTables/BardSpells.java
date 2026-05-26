package com.verdantsoftware.lootforge.TypesOfLoot.SpellTables;

/**
 * Created by PaulD on 2015-11-30.
 */
public class BardSpells extends AbstractSpells implements Spells {

    protected String[] level0 = {"Blade Ward", "Dancing Lights", "Friends", "Light", "Mage Hand", "Mending", "Message", "Minor Illusion", "Prestidigitation", "True Strike", "Vicious Strike"};
    protected String[] level1 = {"Animal Friendship", "Bane", "Charm Person", "Comprehend Languages", "Cure Wounds", "Detect Magic", "Disguise Self", "Dissonant Whispers", "Faerie Fire", "Healing Word", "Heroism", "Identify", "Illusory Script", "Long Strider", "Silent Image", "Sleep", "Speak with Animals", "Tasha's Hideous Laughter", "Thunderwave", "Unseen Servant"};
    protected String[] level2 = {"Animal Messenger", "Blindness/Deafness", "Calm Emotions", "Cloud of Daggers", "Crown of Madness", "Detect thoughts", "Enhance Ability", "Enthrall", "Heat Metal", "Hold Person", "Invisibility", "Knock", "Lesser Restoration", "Locate Animals or Plants", "Locate Object", "Magic Mouth", "Phantasmal Force", "See Invisible", "Shatter", "Silence", "Suggestion", "Zone of Truth"};
    protected String[] level3 = {"Bestow Curse", "Clairvoyance", "Dispel Magic", "Fear", "Feign Death", "Glyph of Warding", "Hypnotic Pattern", "Leomund's Tiny Hut", "Major Image", "Nondetection", "Plant Growth", "Sending", "Speak with Dead", "Speak with Plants", "Stinking Cloud", "Tongues"};
    protected String[] level4 = {"Compulsion", "Confusion", "Dimension Door", "Freedom of Movement", "Greater Invisibility", "Hallucinatory Terrain", "Locate Creature", "Polymorph"};
    protected String[] level5 = {"Animate Objects", "Awaken", "Dominate Person", "Dream", "Geas", "Greater Restoration", "Hold Monster", "Legend Lore", "Mass Cure Wounds", "Mislead", "Modify Memory", "Plant Binding", "Raise Dead", "Scrying", "Seeming", "Teleportation"};
    protected String[] level6 = {"Eyebite", "Find the Path", "Guards and Wards", "Mass Suggestion", "Otto's Irresistible Dance", "Programmed Illusion", "True Seeing"};
    protected String[] level7 = {"Etheralness", "Forcecage", "Mirage Arcane", "Mordenkainen's Magnificent Mansion", "Mordenkainen's sword", "Project Image", "Regenerate", "Resurrection", "Symbol", "Teleport"};
    protected String[] level8 = {"Dominate Monster", "Feeblemind", "Glibness", "Mind Blank", "Power Word Stun"};
    protected String[] level9 = {"Foresight", "Power Word Heal", "Power Word Kill", "True Polymorph"};

    public BardSpells() {
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
