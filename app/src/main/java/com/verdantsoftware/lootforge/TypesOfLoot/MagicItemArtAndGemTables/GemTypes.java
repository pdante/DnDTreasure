package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables;

import com.verdantsoftware.lootforge.Dice.Dice;
import com.verdantsoftware.lootforge.TreasureCreationClasses.AbstractGeneratedStrings;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateArtOrGemString;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.GemTableObject;

/**
 * Created by PaulD on 2015-11-23.
 */
public class GemTypes extends ValuableItems {
    Dice d = new Dice();
    int number;
    GemTableObject gemTableObject = new GemTableObject();
    String gemName = "";
    String valueString = "";

    @Override
    public GemTableObject getItem(int value) {
        int option;
        switch (value) {
            case 10:
                number = d.roll(12);
                valueString = " Value 10 GP.";
                switch (number) {
                    case 1:
                        gemName = "Azurite (opaque mottled deep blue).";
                        break;
                    case 2:
                        option = d.roll(4);
                        if (option == 1)
                            gemName = "Banded agate (translucent striped brown).";
                        else if (option == 2)
                            gemName = "Banded agate (translucent striped blue).";
                        else if (option == 3)
                            gemName = "Banded agate (translucent striped white).";
                        else
                            gemName = "Banded agate (translucent striped red).";
                        break;
                    case 3:
                        gemName = "Blue quartz (transparent pale blue).";
                        break;
                    case 4:
                        option = d.roll(5);
                        if (option == 1)
                            gemName = "Eye agate (translucent circles of brown).";
                        else if (option == 2)
                            gemName = "Eye agate (translucent circles of blue).";
                        else if (option == 3)
                            gemName = "Eye agate (translucent circles of white).";
                        else if (option == 4)
                            gemName = "Eye agate (translucent circles of red).";
                        else
                            gemName = "Eye agate (translucent circles of grey).";
                        break;
                    case 5:
                        gemName = "Hematite (opaque grey-black).";
                        break;
                    case 6:
                        gemName = "Lapis lazuli (opaque light and dark blue with yellow flecks).";
                        break;
                    case 7:
                        gemName = "Malachite (opaque striated light and dark green).";
                        break;
                    case 8:
                        option = d.roll(4);
                        if (option == 1)
                            gemName = "Moss agate (translucent pink with mossy gray markings).";
                        else if (option == 2)
                            gemName = "Moss agate (translucent pink with green markings).";
                        else if (option == 3)
                            gemName = "Moss agate (translucent yellow-white with mossy gray markings).";
                        else
                            gemName = "Moss agate (translucent yellow-white with green markings).";
                        break;
                    case 9:
                        gemName = "Obsidian (opaque black).";
                        break;
                    case 10:
                        gemName = "Rhodochrosite (opaque light pink).";
                        break;
                    case 11:
                        gemName = "Tiger eye (translucent brown with golden center).";
                        break;
                    default:
                        gemName = "Turquoise (opaque light blue-green).";
                        break;
                }
                break;
            case 50:
                number = d.roll(12);
                valueString = " Value 50 GP.";
                switch (number) {
                    case 1:
                        gemName = "Bloodstone (opaque dark gray with red flecks).";
                        break;
                    case 2:
                        option = d.roll(2);
                        if (option == 1)
                            gemName = "Carnelian (opaque orange).";
                        else
                            gemName = "Carnelian (opaque red-brown).";
                        break;
                    case 3:
                        gemName = "Chalcedony (opaque white).";
                        break;
                    case 4:
                        gemName = "Chrysoprase (translucent green).";
                        break;
                    case 5:
                        gemName = "Citrine (transparent pale yellow-brown).";
                        break;
                    case 6:
                        option = d.roll(3);
                        if (option == 1)
                            gemName = "Jasper (opaque blue).";
                        else if (option == 2)
                            gemName = "Jasper (opaque black).";
                        else
                            gemName = "Jasper (opaque brown).";
                        break;
                    case 7:
                        gemName = "Moonstone (translucent white with pale blue glow).";
                        break;
                    case 8:
                        option = d.roll(3);
                        if (option == 1)
                            gemName = "Onyx (opaque bands of black and white).";
                        else if (option == 2)
                            gemName = "Onyx (pure black).";
                        else
                            gemName = "Onyx (pure white).";
                        break;
                    case 9:
                        option = d.roll(3);
                        if (option == 1)
                            gemName = "Quartz (transparent white).";
                        else if (option == 2)
                            gemName = "Quartz (transparent smokey gray).";
                        else
                            gemName = "Quartz (transparent yellow).";
                        break;
                    case 10:
                        gemName = "Sardonyx (opaque bands of red and white).";
                        break;
                    case 11:
                        gemName = "Star rose quartz (translucent rosy stone with white star-shaped center).";
                        break;
                    default:
                        gemName = "Zircon (transparent pale blue-green).";
                        break;
                }
                break;
            case 100:
                number = d.roll(10);
                valueString = " Value 10 GP.";
                switch (number) {
                    case 1:
                        option = d.roll(2);
                        if (option == 1)
                            gemName = "Amber (transparent watery gold).";
                        else
                            gemName = "Amber (transparent rich gold).";
                        break;
                    case 2:
                        gemName = "Amethyst (transparent deep purple).";
                        break;
                    case 3:
                        option = d.roll(2);
                        if (option == 1)
                            gemName = "Chrysoberyl (transparent yellow-green).";
                        else
                            gemName = "Chrysoberyl (transparent pale green).";
                        break;
                    case 4:
                        gemName = "Coral (opaque crimson).";
                        break;
                    case 5:
                        option = d.roll(3);
                        if (option == 1)
                            gemName = "Garnet (transparent red).";
                        else if (option == 2)
                            gemName = "Garnet (transparent brown-green).";
                        else
                            gemName = "Garnet (transparent violet).";
                        break;
                    case 6:
                        option = d.roll(3);
                        if (option == 1)
                            gemName = "Jade (translucent light green).";
                        else if (option == 2)
                            gemName = "Jade (translucent deep green).";
                        else
                            gemName = "Jade (translucent white).";
                        break;
                    case 7:
                        gemName = "Jet (opaque deep black).";
                        break;
                    case 8:
                        option = d.roll(3);
                        if (option == 1)
                            gemName = "Pearl (opaque lustrous white).";
                        else if (option == 2)
                            gemName = "Pearl (opaque lustrous yellow).";
                        else
                            gemName = "Pearl (opaque lustrous pink).";
                        break;
                    case 9:
                        option = d.roll(3);
                        if (option == 1)
                            gemName = "Spinel (transparent red).";
                        else if (option == 2)
                            gemName = "Spinel (transparent red-brown).";
                        else
                            gemName = "Spinel (transparent deep green).";
                        break;
                    default:
                        option = d.roll(4);
                        if (option == 1)
                            gemName = "Tourmaline (transparent pale green).";
                        else if (option == 2)
                            gemName = "Tourmaline (transparent blue).";
                        else if (option == 3)
                            gemName = "Tourmaline (transparent brown).";
                        else
                            gemName = "Tourmaline (transparent red).";
                        break;
                }
                break;
            case 500:
                number = d.roll(6);
                valueString = " Value 500 GP.";
                switch (number) {
                    case 1:
                        gemName = "Alexandrite (transparent dark green).";
                        break;
                    case 2:
                        gemName = "Aquamarine (transparent pale blue-green).";
                        break;
                    case 3:
                        gemName = "Black pearl (opaque pure black).";
                        break;
                    case 4:
                        gemName = "Blue spinel (transparent deep blue).";
                        break;
                    case 5:
                        gemName = "Peridot (transparent rich olive green).";
                        break;
                    default:
                        gemName = "Topaz (transparent golden yellow).";
                        break;
                }
                break;
            case 1000:
                number = d.roll(8);
                valueString = " Value 1000 GP.";
                switch (number) {
                    case 1:
                        gemName = "Black opal (translucent dark green with black mottling and golden flecks).";
                        break;
                    case 2:
                        option = d.roll(2);
                        if (option == 1)
                            gemName = "Blue sapphire (transparent blue-white).";
                        else
                            gemName = "Blue sapphire (transparent medium blue).";
                        break;
                    case 3:
                        option = d.roll(2);
                        if (option == 1)
                            gemName = "Chrysoberyl (transparent yellow-green).";
                        else
                            gemName = "Emerald (transparent deep bright green).";
                        break;
                    case 4:
                        gemName = "Fire opal (translucent fiery red).";
                        break;
                    case 5:
                        gemName = "Opal (translucent pale blue with green and golden mottling).";
                        break;
                    case 6:
                        gemName = "Star ruby (translucent ruby with white star-shaped center).";
                        break;
                    case 7:
                        gemName = "Star sapphire (translucent blue sapphire with white star-shaped center).";
                        break;
                    case 8:
                        option = d.roll(2);
                        if (option == 1)
                            gemName = "Yellow sapphire (transparent fiery yellow).";
                        else
                            gemName = "Yellow sapphire (transparent yellow-green).";
                        break;
                }
                break;
            default: //5000
                number = d.roll(4);
                valueString = " Value 5000 GP.";
                switch (number) {
                    case 1:
                        gemName = "Black sapphire (translucent lustrous black with glowing highlights).";
                        break;
                    case 2:
                        option = d.roll(5);
                        if (option == 1)
                            gemName = "Diamond (transparent blue-white).";
                        else if (option == 2)
                            gemName = "Diamond (transparent canary).";
                        else if (option == 3)
                            gemName = "Diamond (transparent pink).";
                        else if (option == 4)
                            gemName = "Diamond (transparent brown).";
                        else
                            gemName = "Diamond (transparent blue).";
                        break;
                    case 3:
                        gemName = "Jacinth (transparent fiery orange).";
                        break;
                    default:
                        option = d.roll(2);
                        if (option == 1)
                            gemName = "Ruby (transparent clear red).";
                        else
                            gemName = "Ruby (transparent deep crimson).";
                        break;
                }
                break;
        }
        AbstractGeneratedStrings generatedStrings = new GenerateArtOrGemString();
        //TODO not sure why the gem value got removed, but temp fixed it
        generatedStrings.setName(gemName);
        generatedStrings.setMagicItemtable(valueString);
        gemTableObject.generatedStrings = generatedStrings;
        return gemTableObject;
    }
}
