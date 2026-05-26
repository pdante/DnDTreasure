package com.verdantsoftware.lootforge.TypesOfLoot.DamageTypesAndMonsterTypes;

import com.verdantsoftware.lootforge.Dice.Dice;

/**
 * Created by PaulD on 2015-11-26.
 */
public class DamageType {
    Dice d = new Dice();
    int number;
    String damageType;

    public DamageType() {
        d = new Dice();
    }

    public String getDamageType() {
        number = d.roll(10);
        switch (number) {
            case 1:
                damageType = "acid";
                break;
            case 2:
                damageType = "cold";
                break;
            case 3:
                damageType = "fire";
                break;
            case 4:
                damageType = "force";
                break;
            case 5:
                damageType = "lightning";
                break;
            case 6:
                damageType = "necrotic";
                break;
            case 7:
                damageType = "poison";
                break;
            case 8:
                damageType = "psychic";
                break;
            case 9:
                damageType = "radiant";
                break;
            default:
                damageType = "thunder";
                break;
        }
        return damageType;
    }
}