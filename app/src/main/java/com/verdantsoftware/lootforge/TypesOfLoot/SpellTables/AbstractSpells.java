package com.verdantsoftware.lootforge.TypesOfLoot.SpellTables;

import com.verdantsoftware.lootforge.Dice.Dice;

/**
 * Created by PaulD on 2015-11-30.
 */
public abstract class AbstractSpells {
    protected String[] spells;
    protected int number;
    Dice d = new Dice();

    public abstract String getSpell (int level);
    public abstract String[] getSpells(int level);
    public String getSpell(int level, AbstractSpells abstractSpells) {
        switch (level) {
            case 0:
                spells = getSpells(0);
                break;
            case 1:
                spells = getSpells(1);
                break;
            case 2:
                spells = getSpells(2);
                break;
            case 3:
                spells = getSpells(3);
                break;
            case 4:
                spells = getSpells(4);
                break;
            case 5:
                spells = getSpells(5);
                break;
            case 6:
                spells = getSpells(6);
                break;
            case 7:
                spells = getSpells(7);
                break;
            case 8:
                spells = getSpells(8);
                break;
            default:
                spells = getSpells(9);
                break;
        }
        number = spells.length;
        number = d.roll(number)-1;
        return spells[number];
    }
}
