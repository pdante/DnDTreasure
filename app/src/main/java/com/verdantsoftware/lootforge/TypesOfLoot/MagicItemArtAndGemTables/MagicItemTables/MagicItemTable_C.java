package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables;

import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateItemStrings;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateSpell;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateSpellStrings;
import com.verdantsoftware.lootforge.TypesOfLoot.DamageTypesAndMonsterTypes.MonsterType;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.MagicItemTableObject;

/**
 * Created by PaulD on 2015-11-26.
 */
public class MagicItemTable_C extends AbstractMagicItemTable implements MagicItemTable {

    public MagicItemTableObject getItem(int number) {
        MonsterType monsterType = new MonsterType();

        if (number < 16) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of superior healing");
        } else if (number < 23) {
            spells = new GenerateSpell(4);
            generatedStrings = new GenerateSpellStrings();
            generatedStrings = spells.generateSpell();
        } else if (number < 28) {
            generatedStrings = new GenerateItemStrings();
            magicItemTableObject.numberOfItem = d.roll(4);
            generatedStrings.setName("Ammunition, +2");
        } else if (number < 33) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of clairvoyance");
        } else if (number < 38) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of diminution");
        } else if (number < 43) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of gaseous form");
        } else if (number < 48) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of frost giant strength");
        } else if (number < 53) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of stone giant strength");
        } else if (number < 58) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of heroism");
        } else if (number < 63) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of invulnerability");
        } else if (number < 68) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of mind reading");
        } else if (number < 73) {
            spells = new GenerateSpell(5);
            generatedStrings = new GenerateSpellStrings();
            generatedStrings = spells.generateSpell();
        } else if (number < 76) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Elixir of health");
        } else if (number < 79) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Oil of etherealness");
        } else if (number < 82) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of fire giant strength");
        } else if (number < 85) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Quaal's feather token");
        } else if (number < 88) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Scroll of protection from " + monsterType.getMonsterType());
        } else if (number < 90) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Bag of beans");
        } else if (number < 92) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Bead of force");
        } else if (number == 92) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Chime of opening");
        } else if (number == 93) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Decanter of endless water");
        } else if (number == 94) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Eyes of minute seeing");
        } else if (number == 95) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Folding boat");
        } else if (number == 96) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Heward's handy haversack");
        } else if (number == 97) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Horseshoes of speed");
        } else if (number == 98) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Necklace of fireballs");
        } else if (number == 99) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Periapt of health");
        } else {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Sending stones");
        }

        generatedStrings.setMagicItemtable("(Table C)");
        magicItemTableObject.generatedStrings=generatedStrings;
        return magicItemTableObject;
    }
}