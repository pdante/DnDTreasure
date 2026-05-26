package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables;

import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateItemStrings;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateSpell;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateSpellStrings;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.MagicItemTableObject;

/**
 * Created by PaulD on 2015-11-26.
 */
public class MagicItemTable_E extends AbstractMagicItemTable implements MagicItemTable {

    public MagicItemTableObject getItem(int number) {

        if (number < 31) {
            spells = new GenerateSpell(8);
            generatedStrings = new GenerateSpellStrings();
            generatedStrings = spells.generateSpell();
        } else if (number < 56) {
            generatedStrings = new GenerateItemStrings();
             generatedStrings.setName("Potion of storm giant strength");
        } else if (number < 71) {
            generatedStrings = new GenerateItemStrings();
             generatedStrings.setName("Potion of supreme healing");
        } else if (number < 86) {
            spells = new GenerateSpell(9);
            generatedStrings = new GenerateSpellStrings();
            generatedStrings = spells.generateSpell();
        } else if (number < 94) {
            generatedStrings = new GenerateItemStrings();
             generatedStrings.setName("Universal solvent");
        } else if (number < 99) {
            generatedStrings = new GenerateItemStrings();
             generatedStrings.setName("Arrow of slaying");
        } else {
            generatedStrings = new GenerateItemStrings();
             generatedStrings.setName("Sovereign glue");
        }

        generatedStrings.setMagicItemtable("(Table E)");
        magicItemTableObject.generatedStrings=generatedStrings;
        return magicItemTableObject;
    }
}