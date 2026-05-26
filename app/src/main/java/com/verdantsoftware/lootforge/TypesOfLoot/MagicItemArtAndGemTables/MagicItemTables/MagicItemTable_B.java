package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables;

import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateItemStrings;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateSpell;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateSpellStrings;
import com.verdantsoftware.lootforge.TypesOfLoot.DamageTypesAndMonsterTypes.DamageType;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.MagicItemTableObject;

/**
 * Created by PaulD on 2015-11-26.
 */
public class MagicItemTable_B extends AbstractMagicItemTable implements MagicItemTable {
    DamageType damageType = new DamageType();


    public MagicItemTableObject getItem(int number) {
        if (number < 16) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of greater healing");
        } else if (number < 23) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of fire breath");
        } else if (number < 30) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of " + damageType.getDamageType() + " resistance");
        } else if (number < 35) {
            generatedStrings = new GenerateItemStrings();
            magicItemTableObject.numberOfItem = d.roll(6);
            generatedStrings.setName("Ammunition, +1");
        } else if (number < 40) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of animal friendship");
        } else if (number < 45) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of hill giant strength");
        } else if (number < 50) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of growth");
        } else if (number < 55) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of water breathing");
        } else if (number < 60) {
            spells = new GenerateSpell(2);
            generatedStrings = new GenerateSpellStrings();
            generatedStrings = spells.generateSpell();
        } else if (number < 65) {
            spells = new GenerateSpell(3);
            generatedStrings = new GenerateSpellStrings();
            generatedStrings = spells.generateSpell();
        } else if (number < 68) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Bag of holding");
        } else if (number < 71) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Keoghtom's ointment");
        } else if (number < 74) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Oil of slipperiness");
        } else if (number < 76) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Dust of disappearance");
        } else if (number < 78) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Dust of dryness");
        } else if (number < 80) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Dust of sneezing and choking");
        } else if (number < 82) {
            generatedStrings = new GenerateItemStrings();
            secondary = d.roll(4);
            if (secondary == 1)
                generatedStrings.setName("Blue sapphire elemental gem (air elemental)");
            else if (secondary == 2)
                generatedStrings.setName("Yellow diamond elemental gem (earth elemental)");
            else if (secondary == 3)
                generatedStrings.setName("Red corundum elemental gem (fire elemental)");
            else
                generatedStrings.setName("Emerald elemental gem (water elemental)");
        } else if (number < 84) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Philter of love");
        } else if (number == 84) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Alchemy jug");
        } else if (number == 85) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Cap of water breathing");
        } else if (number == 86) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Cloak of manta ray");
        } else if (number == 87) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Driftglobe");
        } else if (number == 88) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Goggles of night");
        } else if (number == 89) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Helm of comprehending languages");
        } else if (number == 90) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Immovable rod");
        } else if (number == 91) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Lantern of revealing");
        } else if (number == 92) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Mariner's armor");
        } else if (number == 93) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Mithral armor");
        } else if (number == 94) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Potion of poison");
        } else if (number == 95) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Ring of swimming");
        } else if (number == 96) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Robe of useful items");
        } else if (number == 97) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Rope of climbing");
        } else if (number == 98) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Saddle of the cavalier");
        } else if (number == 99) {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Wand of magic detection");
        } else {
            generatedStrings = new GenerateItemStrings();
            generatedStrings.setName("Wand of secrets");
        }


        generatedStrings.setMagicItemtable("(Table B)");
        magicItemTableObject.generatedStrings=generatedStrings;
        return magicItemTableObject;
    }
}