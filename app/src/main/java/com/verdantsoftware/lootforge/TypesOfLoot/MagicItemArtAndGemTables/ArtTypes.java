package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables;

import com.verdantsoftware.lootforge.Dice.Dice;
import com.verdantsoftware.lootforge.TreasureCreationClasses.AbstractGeneratedStrings;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateArtOrGemString;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.ArtTableObject;

/**
 * Created by PaulD on 2015-11-23.
 */
public class ArtTypes extends ValuableItems {
    Dice d = new Dice();
    int number;
    ArtTableObject artTableObject = new ArtTableObject();
    String artName = "";
    String valueString = "";
    @Override
    public ArtTableObject getItem(int value) {
        switch (value) {
            case 25:
                valueString = " Value 25 GP.";
                number = d.roll(10);
                switch (number) {
                    case 1:
                        artName = "Silver ewer.";
                        break;
                    case 2:
                        artName = "Carved bone statuette.";
                        break;
                    case 3:
                        artName = "Small gold bracelet.";
                        break;
                    case 4:
                        artName = "Cloth-of-gold vestments.";
                        break;
                    case 5:
                        artName = "Black velvet mask stitched with silver thread.";
                        break;
                    case 6:
                        artName = "Copper chalice with silver filigree.";
                        break;
                    case 7:
                        artName = "Pair of engraved bone dice.";
                        break;
                    case 8:
                        artName = "Small mirror set in a painted wooden frame.";
                        break;
                    case 9:
                        artName = "Embroidered silk handkerchief.";
                        break;
                    default:
                        artName = "Gold locket with a painted portrait inside.";
                        break;
                }
                break;
            case 250:
                valueString = " Value 250 GP.";
                number = d.roll(10);
                switch (number) {
                    case 1:
                        artName = "Gold ring set with bloodstones.";
                        break;
                    case 2:
                        artName = "Carved ivory statuette.";
                        break;
                    case 3:
                        artName = "Large gold bracelet.";
                        break;
                    case 4:
                        artName = "Silver necklace with a gemstone pendant.";
                        break;
                    case 5:
                        artName = "Bronze crown.";
                        break;
                    case 6:
                        artName = "Silk robe with gold embroidery.";
                        break;
                    case 7:
                        artName = "Large well-made tapestry.";
                        break;
                    case 8:
                        artName = "Brass mug with jade inlay.";
                        break;
                    case 9:
                        artName = "Box of turquoise animal figurines.";
                        break;
                    default:
                        artName = "Gold bird cage with electrum filigree.";
                        break;
                }
                break;
            case 750:
                valueString = " Value 750 GP.";
                number = d.roll(10);
                switch (number) {
                    case 1:
                        artName = "Silver chalice set with moonstones.";
                        break;
                    case 2:
                        artName = "Silver-plated steellongsword with jet set in hilt.";
                        break;
                    case 3:
                        artName = "Carved harp ofexotic wood with ivory inlay and zircon gems.";
                        break;
                    case 4:
                        artName = "Small gold idol.";
                        break;
                    case 5:
                        artName = "Gold dragon comb set with red garnets as eyes.";
                        break;
                    case 6:
                        artName = "Bottle stopper cork embossed with gold leaf and set with amethysts.";
                        break;
                    case 7:
                        artName = "Ceremonialelectrumdaggerwit~ablackpearlin the pommel.";
                        break;
                    case 8:
                        artName = "Silver and gold brooch.";
                        break;
                    case 9:
                        artName = "Obsidian statuette with gold fittings and inlay.";
                        break;
                    default:
                        artName = "Painted gold war mask.";
                        break;
                }
                break;
            case 2500:
                valueString = " Value 2500 GP.";
                number = d.roll(10);
                switch (number) {
                    case 1:
                        artName = "Fine gold chain set with a fire opal.";
                        break;
                    case 2:
                        artName = "Old masterpiece painting.";
                        break;
                    case 3:
                        artName = "Embroidered silk and velvet mantle set with numerous moonstones.";
                        break;
                    case 4:
                        artName = "Platinum bracelet set with a sapphire.";
                        break;
                    case 5:
                        artName = "Embroidered glove set with jewel chips.";
                        break;
                    case 6:
                        artName = "Jeweled anklet.";
                        break;
                    case 7:
                        artName = "Gold music box.";
                        break;
                    case 8:
                        artName = "Gold circlet set with four aquamarines.";
                        break;
                    case 9:
                        artName = "Eye patch with a mock eye set in blue sapphire and moonstone.";
                        break;
                    default:
                        artName = "A necklace string of small pink pearls.";
                        break;
                }
                break;
            default: //7500 GP
                valueString = " Value 7500 GP.";
                number = d.roll(8);
                switch (number) {
                    case 1:
                        artName = "Jeweled gold crown.";
                        break;
                    case 2:
                        artName = "Jeweled platinum ring.";
                        break;
                    case 3:
                        artName = "Small gold statuette set with rubies.";
                        break;
                    case 4:
                        artName = "Gold cup set with emeralds.";
                        break;
                    case 5:
                        artName = "Gold jewelry box with platinum filigree.";
                        break;
                    case 6:
                        artName = "Painted gold child's sarcophagus.";
                        break;
                    case 7:
                        artName = "Jade game board with solid gold playing pieces.";
                        break;
                    default:
                        artName = "Bejeweled ivory drinking horn with gold filigree.";
                        break;
                }
                break;
        }
        AbstractGeneratedStrings generatedStrings = new GenerateArtOrGemString();
        generatedStrings.setName(artName );
        generatedStrings.setMagicItemtable(valueString);
        artTableObject.generatedStrings = generatedStrings;
        return artTableObject;
    }
}