package com.verdantsoftware.lootforge.TreasureCreationClasses;

import com.verdantsoftware.lootforge.Dice.Dice;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.AbstractSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.ArtificerSpells;
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
 *
 * Class slot conventions used by the random-class switch:
 *   1-6  Full casters (Bard, Cleric, Druid, Sorcerer, Warlock, Wizard) — levels 0-9
 *   7    Artificer                                                     — levels 0-5
 *   8-9  Half-casters w/o cantrips (Ranger, Paladin)                   — levels 1-5
 *
 * The random-class draws scale to whichever slots are valid for the
 * requested level (e.g. d6 for high-level scrolls, d7 for cantrips,
 * d9 for an in-range level).
 */
public class GenerateSpell {
    Dice d = new Dice();
    int secondary;
    MagicItemTableObject magicItemTableObject = new MagicItemTableObject();
    AbstractSpells spells;
    int level;
    String spellClass;
    GenerateSpellStrings generateSpellStrings = new GenerateSpellStrings();

    //RANDOM SPELL CLASS AND LEVEL-----------------------------------------------------------------
    public GenerateSpell() {

        secondary = d.roll(9);

        assignClassBySecondary(secondary);

        if (secondary >= 8)                  // Paladin or Ranger
            level = d.roll(5);               // 1-5
        else if (secondary == 7)             // Artificer
            level = d.roll(6) - 1;           // 0-5
        else                                  // full caster
            level = d.roll(10) - 1;          // 0-9

    }

    //RANDOM SPELL LEVEL---------------------------------------------------------------------------
    public GenerateSpell(AbstractSpells spells, String spellClass) {

        this.spells = spells;
        this.spellClass = spellClass;

        if (spellClass.equals("Paladin") || spellClass.equals("Ranger"))
            level = d.roll(5);               // 1-5
        else if (spellClass.equals("Artificer"))
            level = d.roll(6) - 1;           // 0-5
        else
            level = d.roll(10) - 1;          // 0-9

    }

    //RANDOM SPELL CLASS---------------------------------------------------------------------------
    public GenerateSpell(int level) {
        this.level = level;

        if (level == 0)
            secondary = d.roll(7);           // full casters + Artificer (have cantrips)
        else if (level > 5)
            secondary = d.roll(6);           // full casters only (have level 6-9)
        else
            secondary = d.roll(9);           // any class (levels 1-5 are valid for all)

        assignClassBySecondary(secondary);
        generateSpellStrings.setSpellCLass(spellClass + " Spell:");
    }

    //KNOWN CLASS AND LEVEL------------------------------------------------------------------------
    public GenerateSpell(AbstractSpells spells, String spellClass, int level){
        this.spells = spells;
        this.spellClass = spellClass;
        this.level = level;
    }

    private void assignClassBySecondary(int s) {
        switch (s) {
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
                spells = new ArtificerSpells();
                spellClass = "Artificer";
                break;
            case 8:
                spells = new RangerSpells();
                spellClass = "Ranger";
                break;
            default:
                spells = new PaladinSpells();
                spellClass = "Paladin";
                break;
        }
    }

    public GenerateSpellStrings generateSpell() {

        String scroll;

        if ((level == 0) && (spells.getClass() == PaladinSpells.class)) {
            return errorResult("Paladin class does not have any Level 0 spells");
        }
        if ((level == 0) && (spells.getClass() == RangerSpells.class)) {
            return errorResult("Ranger class does not have any Level 0 spells");
        }
        if ((level > 5) && (spells.getClass() == PaladinSpells.class)){
            return errorResult("Paladin class does not have any spells above Level 5");
        }
        if ((level > 5) && (spells.getClass() == RangerSpells.class)){
            return errorResult("Ranger class does not have any spells above Level 5");
        }
        if ((level > 5) && (spells.getClass() == ArtificerSpells.class)){
            return errorResult("Artificer class does not have any spells above Level 5");
        }
        scroll = spells.getSpell(level);
        generateSpellStrings.level = "spell scroll (level " + level + ")";
        generateSpellStrings.name = scroll;
        generateSpellStrings.spellCLass = spellClass;

        return generateSpellStrings;
    }

    /**
     * Build a "no spells of this level" result that still populates the
     * class/level header so the dialog formatting in SpellsFragment
     * (which assumes a class+level line followed by an indented name)
     * doesn't end up with a blank header line and a stray indent.
     */
    private GenerateSpellStrings errorResult(String message) {
        generateSpellStrings.level = "spell scroll (level " + level + ")";
        generateSpellStrings.spellCLass = spellClass;
        generateSpellStrings.setName(message);
        return generateSpellStrings;
    }


}
