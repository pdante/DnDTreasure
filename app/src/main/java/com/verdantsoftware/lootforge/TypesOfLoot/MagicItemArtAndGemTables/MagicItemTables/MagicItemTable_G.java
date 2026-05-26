package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables;

import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateItemStrings;
import com.verdantsoftware.lootforge.TypesOfLoot.DamageTypesAndMonsterTypes.DamageType;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.MagicItemTableObject;

/**
 * Created by PaulD on 2015-11-26.
 */
public class MagicItemTable_G extends AbstractMagicItemTable implements MagicItemTable {
    DamageType damageType = new DamageType();

    public MagicItemTableObject getItem(int number) {
        generatedStrings = new GenerateItemStrings();
        if (number < 12) {
             generatedStrings.setName("Weapon (any), +2");
        } else if (number < 15) {
            secondary = d.roll(8);
            if (secondary == 1)
                 generatedStrings.setName("Bronze griffon");
            else if (secondary == 2)
                 generatedStrings.setName("Ebony fly");
            else if (secondary == 3)
                 generatedStrings.setName("Golden lions");
            else if (secondary == 4)
                 generatedStrings.setName("Ivory goats");
            else if (secondary == 5)
                 generatedStrings.setName("Marble elephant");
            else if (secondary < 8)
                 generatedStrings.setName("Onyx dog");
            else if (secondary == 8)
                 generatedStrings.setName("Serpentine owl");
        } else if (number == 15) {
             generatedStrings.setName("Adamantine armor (breastplate)");
        } else if (number == 16) {
             generatedStrings.setName("Adamantine armor (splint)");
        } else if (number == 17) {
             generatedStrings.setName("Amulet of health");
        } else if (number == 18) {
             generatedStrings.setName("Armor of vulnerability");
        } else if (number == 19) {
             generatedStrings.setName("Arrow-catching shield");
        } else if (number == 20) {
             generatedStrings.setName("Belt of dwarvenkind");
        } else if (number == 21) {
             generatedStrings.setName("Belt of hill giant strength");
        } else if (number == 22) {
             generatedStrings.setName("Berserker axe");
        } else if (number == 23) {
             generatedStrings.setName("Boots of levitation");
        } else if (number == 24) {
             generatedStrings.setName("Boots of speed");
        } else if (number == 25) {
             generatedStrings.setName("Bowl of commanding water elementals");
        } else if (number == 26) {
             generatedStrings.setName("Bracers of defense");
        } else if (number == 27) {
             generatedStrings.setName("Brazier of commanding fire elementals");
        } else if (number == 28) {
             generatedStrings.setName("Cape of the mountebank");
        } else if (number == 29) {
             generatedStrings.setName("Censer of controlling air elementals");
        } else if (number == 30) {
             generatedStrings.setName("Armor, +1 chain mail");
        } else if (number == 31) {
             generatedStrings.setName("Armor of resistance (chain mail)");
        } else if (number == 32) {
             generatedStrings.setName("Armor,+1 chain shirt");
        } else if (number == 33) {
             generatedStrings.setName("Armor of resistance (chain shirt)");
        } else if (number == 34) {
             generatedStrings.setName("Cloak of displacement");
        } else if (number == 35) {
             generatedStrings.setName("Cloak of the bat");
        } else if (number == 36) {
             generatedStrings.setName("Cube of force");
        } else if (number == 37) {
             generatedStrings.setName("Daern's instant fortress");
        } else if (number == 38) {
             generatedStrings.setName("Dagger of venom");
        } else if (number == 39) {
             generatedStrings.setName("Dimensional shackles");
        } else if (number == 40) {
             generatedStrings.setName("Dragon slayer");
        } else if (number == 41) {
             generatedStrings.setName("Elven chain");
        } else if (number == 42) {
             generatedStrings.setName("Flame tongue");
        } else if (number == 43) {
             generatedStrings.setName("Gem of seeing");
        } else if (number == 44) {
             generatedStrings.setName("Giant slayer");
        } else if (number == 45) {
             generatedStrings.setName("Clamoured studded leather");
        } else if (number == 46) {
             generatedStrings.setName("Helm of teleportation");
        } else if (number == 47) {
             generatedStrings.setName("Horn of blasting");
        } else if (number == 48) {
            secondary = d.roll(2);
                    if (secondary == 1)
                         generatedStrings.setName("Horn of Valhalla (silver)");
                    else
                         generatedStrings.setName("Horn of Valhalla (brass)");
        } else if (number == 49) {
             generatedStrings.setName("Instrument of the bards (Canaith mandolin)");
        } else if (number == 50) {
             generatedStrings.setName("Instrument of the bards (Cii lyre)");
        } else if (number == 51) {
             generatedStrings.setName("Ioun stone (awareness)");
        } else if (number == 52) {
             generatedStrings.setName("Ioun stone (protection)");
        } else if (number == 53) {
             generatedStrings.setName("Ioun stone (reserve)");
        } else if (number == 54) {
             generatedStrings.setName("Ioun stone (sustenance)");
        } else if (number == 55) {
             generatedStrings.setName("Iron bands of Bilarro");
        } else if (number == 56) {
             generatedStrings.setName("Armor, +1 leather");
        } else if (number == 57) {
             generatedStrings.setName("Armor of " + damageType.getDamageType() + " resistance (leather)");
        } else if (number == 58) {
             generatedStrings.setName("Mace of disruption");
        } else if (number == 59) {
             generatedStrings.setName("Mace of smiting");
        } else if (number == 60) {
             generatedStrings.setName("Mace of terror");
        } else if (number == 61) {
             generatedStrings.setName("Mantle of spell resistance");
        } else if (number == 62) {
             generatedStrings.setName("Necklace of prayer beads");
        } else if (number == 63) {
             generatedStrings.setName("Periapt of proof against poison");
        } else if (number == 64) {
             generatedStrings.setName("Ring of animal influence");
        } else if (number == 65) {
             generatedStrings.setName("Ring of evasion");
        } else if (number == 66) {
             generatedStrings.setName("Ring of feather falling");
        } else if (number == 67) {
             generatedStrings.setName("Ring of free action");
        } else if (number == 68) {
             generatedStrings.setName("Ring of protection");
        } else if (number == 69) {
             generatedStrings.setName("Ring of " + damageType.getDamageType() + " resistance");
        } else if (number == 70) {
             generatedStrings.setName("Ring of spell storing");
        } else if (number == 71) {
             generatedStrings.setName("Ring of the ram");
        } else if (number == 72) {
             generatedStrings.setName("Ring of X-ray vision");
        } else if (number == 73) {
             generatedStrings.setName("Robe of eyes");
        } else if (number == 74) {
             generatedStrings.setName("Rod of rulership");
        } else if (number == 75) {
             generatedStrings.setName("Rod of the pact keeper, +2");
        } else if (number == 76) {
             generatedStrings.setName("Rope of entanglement");
        } else if (number == 77) {
             generatedStrings.setName("Armor, +1 scale mail");
        } else if (number == 78) {
             generatedStrings.setName("Armor of " + damageType.getDamageType() + " resistance (scale mail)");
        } else if (number == 79) {
             generatedStrings.setName("Shield, +2");
        } else if (number == 80) {
             generatedStrings.setName("Shield of missile attraction");
        } else if (number == 81) {
             generatedStrings.setName("Staff of charming");
        } else if (number == 82) {
             generatedStrings.setName("Staff of healing");
        } else if (number == 83) {
             generatedStrings.setName("Staff of swarming insects");
        } else if (number == 84) {
             generatedStrings.setName("Staff of the woodlands");
        } else if (number == 85) {
             generatedStrings.setName("Staff of withering");
        } else if (number == 86) {
             generatedStrings.setName("Stone of controlling earth elementals");
        } else if (number == 87) {
             generatedStrings.setName("Sun blade");
        } else if (number == 88) {
             generatedStrings.setName("Sword of life stealing");
        } else if (number == 89) {
             generatedStrings.setName("Sword of life stealing");
        } else if (number == 90) {
             generatedStrings.setName("Tentacle rod");
        } else if (number == 91) {
             generatedStrings.setName("Vicious weapon (Any type)");
        } else if (number == 92) {
             generatedStrings.setName("Wand of binding");
        } else if (number == 93) {
             generatedStrings.setName("Wand of enemy detection");
        } else if (number == 94) {
             generatedStrings.setName("Wand of fear");
        } else if (number == 95) {
             generatedStrings.setName("Wand of fireballs");
        } else if (number == 96) {
             generatedStrings.setName("Wand of lightning bolts");
        } else if (number == 97) {
             generatedStrings.setName("Wand of paralysis");
        } else if (number == 98) {
             generatedStrings.setName("Wand of the war mage, +2");
        } else if (number == 99) {
             generatedStrings.setName("Wand of wonder");
        } else {
             generatedStrings.setName("Wings of flying");
        }
        generatedStrings.setMagicItemtable("(Table G)");
        magicItemTableObject.generatedStrings=generatedStrings;
        return magicItemTableObject;
    }
}