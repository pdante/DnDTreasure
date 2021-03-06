package com.dante.paul.dd5erandomlootgeneratorpremium.TreasureCreationClasses;

import com.dante.paul.dd5erandomlootgeneratorpremium.Dice.Dice;
import com.dante.paul.dd5erandomlootgeneratorpremium.EnumeratedClasses.ChallengeRating;
import com.dante.paul.dd5erandomlootgeneratorpremium.LootList;
import com.dante.paul.dd5erandomlootgeneratorpremium.TypesOfLoot.MagicItemArtAndGemTables.JustMagicItems;



/**
 * Created by PaulD on 2015-12-03.
 */
public class GenerateItem implements TreasureTable{
    protected ChallengeRating challengeRating;
    protected int d100;
    protected Dice d = new Dice();
    public LootList list;
    int numberOfItems;

    public GenerateItem(ChallengeRating challengeRating,int numberOfItems) {
        this.numberOfItems = numberOfItems;
        this.challengeRating = challengeRating;
        list = LootList.getInstance();
    }


    @Override
    public void generateTreasure() {
        generateItems();
        list.getItems();
    }
    private void generateItems(){
        for(int counter = 0; counter < numberOfItems; counter++) {
            d100 = d.roll(100);
            JustMagicItems items = new JustMagicItems(challengeRating, d100);
            items.createStuff();
        }
    }
}
