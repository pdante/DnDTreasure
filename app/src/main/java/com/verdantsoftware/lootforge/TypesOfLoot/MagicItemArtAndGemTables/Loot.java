package com.verdantsoftware.lootforge.TypesOfLoot.MagicItemArtAndGemTables;

import com.verdantsoftware.lootforge.Dice.Dice;
import com.verdantsoftware.lootforge.EnumeratedClasses.ChallengeRating;
import com.verdantsoftware.lootforge.EnumeratedClasses.TypeOfEncounter;
import com.verdantsoftware.lootforge.LootList;

/**
 * Created by PaulD on 2015-11-20.
 */
public abstract class Loot {
    public ChallengeRating challenge;
    public LootList list;
    public int d100;
    public Dice d = new Dice();
    TypeOfEncounter typeOfEncounter;

    public Loot(ChallengeRating challengeRating, int d100, TypeOfEncounter typeOfEncounter){
        this.challenge=challengeRating;
        list=LootList.getInstance();
        this.d100=d100;
        this.typeOfEncounter = typeOfEncounter;
    }

}
