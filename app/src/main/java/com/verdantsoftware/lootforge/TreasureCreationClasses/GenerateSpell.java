package com.verdantsoftware.lootforge.TreasureCreationClasses;

import com.verdantsoftware.lootforge.Dice.Dice;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.AbstractSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.BardSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.ClericSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.DruidSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.PaladinSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.RangerSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.SorcererSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.WarlockSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.WizardSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.MagicItemTableObject;

/**
 * Created by PaulD on 2015-11-30.
 */
public class GenerateSpell {
    Dice d = new Dice();
    int secondary;
    MagicItemTableObject magicItemTableObject = new MagicItemTableObject();
    AbstractSpells spells;
    int level;
    String spellClass;
    GenerateSpellStrings generateSpellStrings = new GenerateSpellStrings();

    //RANDOM SELL CLASS AND LEVEL-------------------------------------------------------------------
    public GenerateSpell() {

        secondary = d.roll(8);

        switch (secondary) {
            case 1:
                spells = new BardSpells();
                spellClass = "Bard";
                break;
            case 2:
                spells = new ClericSpells();
                spellClass = "Cleric";
                break;
            case 3:
                spells = new DruidSpells();
                spellClass = "Druid";
                break;
            case 4:
                spells = new SorcererSpells();
                spellClass = "Sorcerer";
                break;
            case 5:
                spells = new WarlockSpells();
                spellClass = "Warlock";
                break;
            case 6:
                spells = new WizardSpells();
                spellClass = "Wizard";
                break;
            case 7:
                spells = new RangerSpells();
                spellClass = "Ranger";
                break;
            default:
                spells = new PaladinSpells();
                spellClass = "Paladin";
                break;
        }
        if (secondary > 6)
            level = d.roll(5);
        else
            level = d.roll(10) - 1;

    }

    //RANDOM SPELL LEVEL---------------------------------------------------------------------------
    public GenerateSpell(AbstractSpells spells, String spellClass) {

        this.spells=spells;
        this.spellClass=spellClass;
        
        if (spellClass.equals("Paladin") || spellClass.equals("Ranger"))
            level = d.roll(5);
        else
            level = d.roll(10) - 1;

    }

    //RANDOM SPELL CLASS---------------------------------------------------------------------------
    public GenerateSpell(int level) {
        this.level = level;
        
        if (level == 0 || level > 5)
            secondary = d.roll(6);
        else
            secondary = d.roll(8);

        switch (secondary) {
            case 1:
                spells = new BardSpells();
                spellClass = "Bard";
                break;
            case 2:
                spells = new ClericSpells();
                spellClass = "Cleric";
                break;
            case 3:
                spells = new DruidSpells();
                spellClass = "Druid";
                break;
            case 4:
                spells = new SorcererSpells();
                spellClass = "Sorcerer";
                break;
            case 5:
                spells = new WarlockSpells();
                spellClass = "Warlock";
                break;
            case 6:
                spells = new WizardSpells();
                spellClass = "Wizard";
                break;
            case 7:
                spells = new RangerSpells();
                spellClass = "Ranger";
                break;
            default:
                spells = new PaladinSpells();
                spellClass = "Paladin";
                break;
        }
        generateSpellStrings.setSpellCLass(spellClass + " Spell:");
    }

    //KNOWN CLASS AND LEVEL------------------------------------------------------------------------
    public GenerateSpell(AbstractSpells spells, String spellClass, int level){
        this.spells = spells;
        this.spellClass = spellClass;
        this.level = level;
    }
    public GenerateSpellStrings generateSpell() {

        String scroll;

        if ((level == 0) && ((spells.getClass() == PaladinSpells.class) || (spells.getClass() == RangerSpells.class))) {
            generateSpellStrings.setName("This class does not have any Level 0 spells");
            return generateSpellStrings;
        }
        if ((level > 5) && ((spells.getClass() == PaladinSpells.class) || (spells.getClass() == RangerSpells.class))){
            generateSpellStrings.setName("This class does not have any spells above Level 5");
            return generateSpellStrings;
    }
        scroll = spells.getSpell(level);
        generateSpellStrings.level = "spell scroll (level " + level + ")";
        generateSpellStrings.name = scroll;
        generateSpellStrings.spellCLass = spellClass;

        return generateSpellStrings;
    }


}
