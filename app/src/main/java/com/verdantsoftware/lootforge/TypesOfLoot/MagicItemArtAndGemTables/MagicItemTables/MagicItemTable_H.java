package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables;

import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateItemStrings;
import com.verdantsoftware.lootforge.TypesOfLoot.DamageTypesAndMonsterTypes.DamageType;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.MagicItemTableObject;

/**
 * Created by PaulD on 2015-11-26.
 */
public class MagicItemTable_H extends AbstractMagicItemTable implements MagicItemTable {
    DamageType damageType = new DamageType();


    public MagicItemTableObject getItem(int number) {
        generatedStrings = new GenerateItemStrings();
        if (number < 11) {
            generatedStrings.setName("Weapon (any), +3");
        } else if (number < 13) {
            generatedStrings.setName("Amulet of the planes");
        } else if (number < 15) {
            generatedStrings.setName("Carpet of flying");
        } else if (number < 17) {
            generatedStrings.setName("Crystal ball (very rare version)");
        } else if (number < 19) {
            generatedStrings.setName("Ring of regeneration");
        } else if (number < 21) {
            generatedStrings.setName("Ring of shooting stars");
        } else if (number < 23) {
            generatedStrings.setName("Ring of telekinesis");
        } else if (number < 25) {
            generatedStrings.setName("Robe of scintillating colors");
        } else if (number < 27) {
            generatedStrings.setName("Robe of stars");
        } else if (number < 29) {
            generatedStrings.setName("Rod of absorption");
        } else if (number < 31) {
            generatedStrings.setName("Rod of alertness");
        } else if (number < 33) {
            generatedStrings.setName("Rod of security");
        } else if (number < 35) {
            generatedStrings.setName("Rod of the pact keeper, +3");
        } else if (number < 37) {
            generatedStrings.setName("Scimitar of speed");
        } else if (number < 39) {
            generatedStrings.setName("Shield, +3");
        } else if (number < 41) {
            generatedStrings.setName("Staff of fire");
        } else if (number < 43) {
            generatedStrings.setName("Staff of frost");
        } else if (number < 45) {
            generatedStrings.setName("Staff of power");
        } else if (number < 47) {
            generatedStrings.setName("Staff of striking");
        } else if (number < 49) {
            generatedStrings.setName("Staff of thunder and lightning");
        } else if (number < 51) {
            generatedStrings.setName("Sword of sharpness");
        } else if (number < 53) {
            generatedStrings.setName("Wand of polymorph");
        } else if (number < 55) {
            generatedStrings.setName("Wand of the war mage, +3");
        } else if (number == 55) {
            generatedStrings.setName("Adamantine armor (half plate)");
        } else if (number == 56) {
            generatedStrings.setName("Adamantine armor (plate)");
        } else if (number == 57) {
            generatedStrings.setName("Animated shield");
        } else if (number == 58) {
            generatedStrings.setName("Belt of fire giant strength");
        } else if (number == 59) {
            generatedStrings.setName("Belt of frost (or stone) giant strength");
        } else if (number == 60) {
            generatedStrings.setName("Armor, +1 breastplate");
        } else if (number == 61) {
            generatedStrings.setName("Armor of " + damageType.getDamageType() + " resistance (breastplate)");
        } else if (number == 62) {
            generatedStrings.setName("Candle of invocation");
        } else if (number == 63) {
            generatedStrings.setName("Armor, +2 chain mail");
        } else if (number == 64) {
            generatedStrings.setName("Armor, +2 chain shirt");
        } else if (number == 65) {
            generatedStrings.setName("Cloak of arachnida");
        } else if (number == 66) {
            generatedStrings.setName("Dancing sword");
        } else if (number == 67) {
            generatedStrings.setName("Demon armor");
        } else if (number == 68) {
            generatedStrings.setName("Dragon scale mail");
        } else if (number == 69) {
            generatedStrings.setName("Dwarven plate");
        } else if (number == 70) {
            generatedStrings.setName("Dwarven thrower");
        } else if (number == 71) {
            generatedStrings.setName("Efreeti bottle");
        } else if (number == 72) {
            generatedStrings.setName("Figurine of wondrous power (obsidian steed)");
        } else if (number == 73) {
            generatedStrings.setName("Frost brand");
        } else if (number == 74) {
            generatedStrings.setName("Helm of brilliance");
        } else if (number == 75) {
            generatedStrings.setName("Horn of Valhalla (bronze)");
        } else if (number == 76) {
            generatedStrings.setName("Instrument of the bards (Anstruth harp)");
        } else if (number == 77) {
            generatedStrings.setName("Ioun stone (absorption)");
        } else if (number == 78) {
            generatedStrings.setName("Ioun stone (agility)");
        } else if (number == 79) {
            generatedStrings.setName("Ioun stone (fortitude)");
        } else if (number == 80) {
            generatedStrings.setName("Ioun stone (insight)");
        } else if (number == 81) {
            generatedStrings.setName("Ioun stone (intellect)");
        } else if (number == 82) {
            generatedStrings.setName("Ioun stone (leadership)");
        } else if (number == 83) {
            generatedStrings.setName("Ioun stone (strength)");
        } else if (number == 84) {
            generatedStrings.setName("Armor, +2 leather");
        } else if (number == 85) {
            generatedStrings.setName("Manual of bodily health");
        } else if (number == 86) {
            generatedStrings.setName("Manual of gainful exercise");
        } else if (number == 87) {
           secondary= d.roll(20);
            generatedStrings.setName("Manual of golems");
            if (secondary < 6)
                generatedStrings.setName(generatedStrings.getName() + " (clay)");
            else if (secondary < 18)
                generatedStrings.setName(generatedStrings.getName() + " (flesh)");
            else if (secondary == 18)
                generatedStrings.setName(generatedStrings.getName() + " (iron)");
            else
                generatedStrings.setName(generatedStrings.getName() + " (stone)");
            generatedStrings.setName(generatedStrings.getName() + " or DM decides)");
        } else if (number == 88) {
            generatedStrings.setName("Manual of quickness of action");
        } else if (number == 89) {
            generatedStrings.setName("Mirror of life trapping");
        } else if (number == 90) {
            generatedStrings.setName("Nine lives stealer");
        } else if (number == 91) {
            generatedStrings.setName("Oath bow");
        } else if (number == 92) {
            generatedStrings.setName("Armor, +2 scale mail");
        } else if (number == 93) {
            generatedStrings.setName("Spellguard shield");
        } else if (number == 94) {
            generatedStrings.setName("Armor, +1 splint");
        } else if (number == 95) {
            generatedStrings.setName("Armor of resistance (splint)");
        } else if (number == 96) {
            generatedStrings.setName("Armor, +1 studded leather");
        } else if (number == 97) {
            generatedStrings.setName("Armor of " + damageType.getDamageType() + " resistance (studded leather)");
        } else if (number == 98) {
            generatedStrings.setName("Tome of clear thought");
        } else if (number == 99) {
            generatedStrings.setName("Tome of leadership and influence");
        } else {
            generatedStrings.setName("Tome of understanding");
        }
        generatedStrings.setMagicItemtable("(Table H)");
        magicItemTableObject.generatedStrings = generatedStrings;
        return magicItemTableObject;
    }
}
