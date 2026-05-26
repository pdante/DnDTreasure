package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables;

import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateItemStrings;
import com.verdantsoftware.lootforge.TypesOfLoot.DamageTypesAndMonsterTypes.DamageType;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.MagicItemTableObject;

/**
 * Created by PaulD on 2015-11-26.
 */
public class MagicItemTable_I extends AbstractMagicItemTable implements MagicItemTable {

    DamageType damageType = new DamageType();

    public MagicItemTableObject getItem(int number) {
        generatedStrings = new GenerateItemStrings();
        if (number < 6) {
             generatedStrings.setName("Defender");
        } else if (number < 11) {
             generatedStrings.setName("Hammer of thunderbolts");
        } else if (number < 16) {
             generatedStrings.setName("Luck blade");
        } else if (number < 21) {
             generatedStrings.setName("Sword of answering");
        } else if (number < 24) {
             generatedStrings.setName("Holy avenger");
        } else if (number < 27) {
             generatedStrings.setName("Ring of djinni summoning");
        } else if (number < 30) {
             generatedStrings.setName("Ring of invisibility");
        } else if (number < 33) {
             generatedStrings.setName("Ring of spell turning");
        } else if (number < 36) {
             generatedStrings.setName("Rod of lordly might");
        } else if (number < 39) {
             generatedStrings.setName("Staff of the magi");
        } else if (number < 42) {
             generatedStrings.setName("Vorpal sword");
        } else if (number < 44) {
             generatedStrings.setName("Belt of cloud giant strength");
        } else if (number < 46) {
             generatedStrings.setName("Armor, +2 breastplate");
        } else if (number < 48) {
             generatedStrings.setName("Armor, +3 chain mail");
        } else if (number < 50) {
             generatedStrings.setName("Armor, +3 chain shirt");
        } else if (number < 52) {
             generatedStrings.setName("Cloak of invisibility");
        } else if (number < 54) {
             generatedStrings.setName("Crystal ball (legendary version)");
        } else if (number < 56) {
             generatedStrings.setName("Armor, +1 half plate");
        } else if (number < 58) {
             generatedStrings.setName("Iron flask");
        } else if (number < 60) {
             generatedStrings.setName("Armor, +3 leather");
        } else if (number < 62) {
             generatedStrings.setName("Armor, +1 plate");
        } else if (number < 64) {
             generatedStrings.setName("Robe of the archmagi");
        } else if (number < 66) {
             generatedStrings.setName("Rod of resurrection");
        } else if (number < 68) {
             generatedStrings.setName("Armor, +1 scale mail");
        } else if (number < 70) {
             generatedStrings.setName("Scarab of protection");
        } else if (number < 72) {
             generatedStrings.setName("Armor, +2 splint");
        } else if (number < 74) {
             generatedStrings.setName("Armor, +2 studded leather");
        } else if (number < 76) {
             generatedStrings.setName("Well of many worlds");
        } else if (number == 76) {
            secondary = d.roll(12);
            if (secondary < 3)
                 generatedStrings.setName("Armor, +2 half plate");
            else if (secondary < 5)
                 generatedStrings.setName("Armor, +2 plate");
            else if (secondary < 7)
                 generatedStrings.setName("Armor, +3 studded leather");
            else if (secondary < 9)
                 generatedStrings.setName("Armor, +3 breastplate");
            else if (secondary < 11)
                 generatedStrings.setName("Armor, +3 splint");
            else if (secondary == 11)
                 generatedStrings.setName("Armor, +3 half plate");
            else
                 generatedStrings.setName("Armor, +3 plate");
        } else if (number == 77) {
             generatedStrings.setName("Apparatus of Kwalish");
        } else if (number == 78) {
             generatedStrings.setName("Armor of invulnerability");
        } else if (number == 79) {
             generatedStrings.setName("Belt of storm giant strength");
        } else if (number == 80) {
             generatedStrings.setName("Cubic gate");
        } else if (number == 81) {
             generatedStrings.setName("Deck of many things");
        } else if (number == 82) {
             generatedStrings.setName("Efreeti chain");
        } else if (number == 83) {
             generatedStrings.setName("Armor of " + damageType.getDamageType() + " resistance (half plate)");
        } else if (number == 84) {
             generatedStrings.setName("Horn of Valhalla (iron)");
        } else if (number == 85) {
             generatedStrings.setName("Instrument of the bards (Ollamh harp)");
        } else if (number == 86) {
             generatedStrings.setName("Ioun stone (greater absorption)");
        } else if (number == 87) {
             generatedStrings.setName("Ioun stone (mastery)");
        } else if (number == 88) {
             generatedStrings.setName("Ioun stone (regeneration)");
        } else if (number == 89) {
             generatedStrings.setName("Plate armor of etherealness");
        } else if (number == 90) {
             generatedStrings.setName("Plate armor of " + damageType.getDamageType() + " resistance");
        } else if (number == 91) {
             generatedStrings.setName("Ring of air elemental command");
        } else if (number == 92) {
             generatedStrings.setName("Ring of earth elemental command");
        } else if (number == 93) {
             generatedStrings.setName("Ring of fire elemental command");
        } else if (number == 94) {
             generatedStrings.setName("Ring of three wishes");
        } else if (number == 95) {
             generatedStrings.setName("Ring of water elemental command");
        } else if (number == 96) {
             generatedStrings.setName("Sphere of annihilation");
        } else if (number == 97) {
             generatedStrings.setName("Talisman of pure good");
        } else if (number == 98) {
             generatedStrings.setName("Talisman of the sphere");
        } else if (number == 99) {
             generatedStrings.setName("Talisman of ultimate evil");
        } else {
             generatedStrings.setName("Tome of the stilled tongue");
        }
        generatedStrings.setMagicItemtable("(Table I)");
        magicItemTableObject.generatedStrings=generatedStrings;
        return magicItemTableObject;
    }
}

