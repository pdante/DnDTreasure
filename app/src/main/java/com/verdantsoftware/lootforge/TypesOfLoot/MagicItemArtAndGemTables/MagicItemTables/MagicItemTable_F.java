package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables.MagicItemTables;

import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateItemStrings;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.MagicItemTableObject;

/**
 * Created by PaulD on 2015-11-26.
 */
public class MagicItemTable_F extends AbstractMagicItemTable implements MagicItemTable {

    public MagicItemTableObject getItem(int number) {
        generatedStrings = new GenerateItemStrings();
        if (number < 16) {
             generatedStrings.setName("Weapon (any), +1");
        } else if (number < 19) {
             generatedStrings.setName("Shield,+1");
        } else if (number < 22) {
             generatedStrings.setName("Sentinel shield");
        } else if (number < 24) {
             generatedStrings.setName("Amulet of proof against detection and location");
        } else if (number < 26) {
             generatedStrings.setName("Boots of elvenkind");
        } else if (number < 28) {
             generatedStrings.setName("Boots of striding and springing");
        } else if (number < 30) {
             generatedStrings.setName("Bracers of archery");
        } else if (number < 32) {
             generatedStrings.setName("Brooch of shielding");
        } else if (number < 34) {
             generatedStrings.setName("Broom of flying");
        } else if (number < 36) {
             generatedStrings.setName("Cloak of elvenkind");
        } else if (number < 38) {
             generatedStrings.setName("Cloak of protection");
        } else if (number < 40) {
             generatedStrings.setName("Gauntlets of ogre power");
        } else if (number < 42) {
             generatedStrings.setName("Hat of disguise");
        } else if (number < 44) {
             generatedStrings.setName("Javelin of lightning");
        } else if (number < 46) {
             generatedStrings.setName("Pearl of power");
        } else if (number < 48) {
             generatedStrings.setName("Rod of the pact keeper, +1");
        } else if (number < 50) {
             generatedStrings.setName("Slippers of spider climbing");
        } else if (number < 52) {
             generatedStrings.setName("Staff of the adder");
        } else if (number < 54) {
             generatedStrings.setName("Staff of the python");
        } else if (number < 56) {
             generatedStrings.setName("Sword of vengeance");
        } else if (number < 58) {
             generatedStrings.setName("Trident of fish command");
        } else if (number < 60) {
             generatedStrings.setName("Wand of magic missiles");
        } else if (number < 62) {
             generatedStrings.setName("Wand of the war mage, +1");
        } else if (number < 64) {
             generatedStrings.setName("Wand of web");
        } else if (number < 66) {
             generatedStrings.setName("Weapon (any) of warning");
        } else if (number == 66) {
             generatedStrings.setName("Adamantine armor (chain mail)");
        } else if (number == 67) {
             generatedStrings.setName("Adamantine armor (chain shirt)");
        } else if (number == 68) {
             generatedStrings.setName("Adamantine armor (scale mail)");
        } else if (number == 69) {
             generatedStrings.setName("Bag of tricks (gray)");
        } else if (number == 70) {
             generatedStrings.setName("Bag of tricks (rust)");
        } else if (number == 71) {
             generatedStrings.setName("Bag of tricks (tan)");
        } else if (number == 72) {
             generatedStrings.setName("Boots of the winterlands");
        } else if (number == 73) {
             generatedStrings.setName("Circlet of blasting");
        } else if (number == 74) {
             generatedStrings.setName("Deck of illusions");
        } else if (number == 75) {
             generatedStrings.setName("Eversmoking bottle");
        } else if (number == 76) {
             generatedStrings.setName("Eyes of charming");
        } else if (number == 77) {
             generatedStrings.setName("Eyes of the eagle");
        } else if (number == 78) {
             generatedStrings.setName("Figurine of wondrous power (silver raven)");
        } else if (number == 79) {
             generatedStrings.setName("Gem of brightness");
        } else if (number == 80) {
             generatedStrings.setName("Gloves of missile snaring");
        } else if (number == 81) {
             generatedStrings.setName("Gloves of swimming and climbing");
        } else if (number == 82) {
             generatedStrings.setName("Gloves of thievery");
        } else if (number == 83) {
             generatedStrings.setName("Headband of intellect");
        } else if (number == 84) {
             generatedStrings.setName("Helm of telepathy");
        } else if (number == 85) {
             generatedStrings.setName("Instrument of the bards (Doss lute)");
        } else if (number == 86) {
             generatedStrings.setName("Instrument of the bards (Fochlucan bandore)");
        } else if (number == 87) {
             generatedStrings.setName("Instrument of the bards (Mac-Fuimidh cittern)");
        } else if (number == 88) {
             generatedStrings.setName("Medallion ofthoughts");
        } else if (number == 89) {
             generatedStrings.setName("Necklace of adaptation");
        } else if (number == 90) {
             generatedStrings.setName("Periapt of wound closure");
        } else if (number == 91) {
             generatedStrings.setName("Pipes of haunting");
        } else if (number == 92) {
             generatedStrings.setName("Pipes of the sewers");
        } else if (number == 93) {
             generatedStrings.setName("Ring of jumping");
        } else if (number == 94) {
             generatedStrings.setName("Ring of mind shielding");
        } else if (number == 95) {
             generatedStrings.setName("Ring of warmth");
        } else if (number == 96) {
             generatedStrings.setName("Ring of water walking");
        } else if (number == 97) {
             generatedStrings.setName("Quiver of Ehlonna");
        } else if (number == 98) {
             generatedStrings.setName("Stone of good luck");
        } else if (number == 99) {
             generatedStrings.setName("Wind fan");
        } else {
             generatedStrings.setName("Winged boots");
        }

        generatedStrings.setMagicItemtable("(Table F)");
        magicItemTableObject.generatedStrings=generatedStrings;
        return magicItemTableObject;
    }
}
