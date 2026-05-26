package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables;

import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateItemStrings;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateSpell;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateSpellStrings;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.MagicItemTableObject;

/**
 * Created by PaulD on 2015-11-26.
 */
public class MagicItemTable_D extends AbstractMagicItemTable implements MagicItemTable {

    public MagicItemTableObject getItem(int number) {

        if (number < 21) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of supreme healing");
        } else if (number < 31) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of invisibility");
        } else if (number < 41) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of speed");
        } else if (number < 51) {
            spells = new GenerateSpell(6);
            generatedStrings = new GenerateSpellStrings();
            generatedStrings = spells.generateSpell();
        } else if (number < 58) {
            spells = new GenerateSpell(7);
            generatedStrings = new GenerateSpellStrings();
            generatedStrings = spells.generateSpell();
        } else if (number < 63) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName(" Ammunition, +3");
        } else if (number < 68) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Oil of sharpness");
        } else if (number < 73) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of flying");
        } else if (number < 78) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of cloud giant strength");
        } else if (number < 83) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of longevity");
        } else if (number < 88) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of vitality");
        } else if (number < 93) {
            spells = new GenerateSpell(8);
            generatedStrings = new GenerateSpellStrings();
            generatedStrings = spells.generateSpell();
        } else if (number < 96) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Horseshoes of a zephyr");
        } else if (number < 99) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Nolzur's marvelous pigments");
        } else if (number == 99) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Bag of devouring");
        } else {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Portable hole");
        }
        generatedStrings.setMagicItemtable("(Table D)");
        magicItemTableObject.generatedStrings=generatedStrings;
        return magicItemTableObject;
    }
}