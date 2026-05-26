package com.verdantsoftware.lootforge.TypesOfLoot.DamageTypesAndMonsterTypes;

import com.verdantsoftware.lootforge.Dice.Dice;

/**
 * Created by PaulD on 2015-11-26.
 */
public class MonsterType {
    Dice d = new Dice();
    String monsterType;
    int number;

    public MonsterType() {
    }

    public String getMonsterType(){
        number = d.roll(100);

        if (number < 11)
            monsterType = "Aberrations";
        else if (number < 21)
            monsterType = "Beasts";
        else if (number < 31)
            monsterType = "Celestials";
        else if (number < 41)
            monsterType = "Elementals";
        else if (number < 51)
            monsterType = "Fey";
        else if (number < 76)
            monsterType = "Fiends";
        else if (number < 81)
            monsterType = "Plants";
        else
            monsterType = "Undead";

        return monsterType;

    }
}
