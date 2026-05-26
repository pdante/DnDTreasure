package com.verdantsoftware.lootforge;

import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.ArtTableObject;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.GemTableObject;
import com.verdantsoftware.lootforge.TypesOfLoot.TableObjects.TableObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by PaulD on 2015-11-20.
 */
public class LootList {

    private Map<String, Integer> coins = new HashMap<>();
    private Map<String, Integer> loot = new HashMap<>();
    private Map<String, Integer> gems = new HashMap<>();
    private Map<String, Integer> art = new HashMap<>();
    private int numberOfItems = 0;

    //Singleton pattern
    private static volatile LootList list = new LootList();

    private LootList() {
        loot = new Hashtable<>();
        coins = new Hashtable<>();
    }

    public static LootList getInstance() {
        return list;
    }

    private String printCoins (String treasure){
        treasure += "\r\n";
        treasure += "Coins: \r\n";
        if (coins.containsKey("cp"))
            treasure += coins.get("cp") + "cp" + "\r\n";
        if (coins.containsKey("sp"))
            treasure += coins.get("sp") + "sp" + "\r\n";
        if (coins.containsKey("ep"))
            treasure += coins.get("ep") + "ep" + "\r\n";
        if (coins.containsKey("gp"))
            treasure += coins.get("gp") + "gp" + "\r\n";
        if (coins.containsKey("pp"))
            treasure += coins.get("pp") + "pp" + "\r\n";
        return treasure;
    }

    private String printTreasure(Map storage, String treasure, String type) {
        treasure += "\r\n";
        treasure += type + "\r\n";
        List keys = new ArrayList(storage.keySet());
        Collections.sort(keys);
        for (Object key : keys) {
            treasure += storage.get(key) + "x " + key + "\r\n";
        }
    return treasure;
    }


    public String getTreasure() {
        LootList list = LootList.getInstance();
        String treasure;
        treasure = "";
        if (!list.getCoins().isEmpty())
            treasure = printCoins(treasure);
        if (!list.getGems().isEmpty())
            treasure = printTreasure(getGems(), treasure, "Gemstones:");
        if (!list.getArt().isEmpty())
            treasure = printTreasure(getArt(), treasure, "Artwork:");
        if (!list.getLoot().isEmpty())
            treasure = printTreasure(getLoot(),treasure, "Items:");
        return treasure;
    }
    public String getItems() {
        String treasure;
        treasure = "";
        treasure = printTreasure(getLoot(),treasure, "Items:");
        return treasure;
    }

    //Takes a magic item and puts it in the loot list
    public void addToLoot(TableObject item) {
        Map<String,Integer> storage;
        String fullNameOfSpell;
        if (item.getClass() == ArtTableObject.class)
            storage = art;
        else if (item.getClass() == GemTableObject.class)
            storage = gems;
        else
            storage = loot;
        if (item.getSpellClass() != ""){
            fullNameOfSpell = item.getSpellClass()+ " " + item.getLevel() + "\r\n " + item.getName();
            item.setName(fullNameOfSpell);
        }
        item.setName(item.getName() + "\r\n" + item.getItemTable());
        if (storage.containsKey(item.getName())) {
            numberOfItems = storage.get(item.getName());
            storage.put(item.getName(), numberOfItems + item.numberOfItem);
        } else
            storage.put(item.getName(), item.numberOfItem);
    }


    //Takes a pile of coins and adds it to the loot list
    public void addToCoins(String coin, int numberOfCoins) {
        if (!coins.isEmpty())
            if (coins.containsKey(coin))
                numberOfCoins = numberOfCoins + coins.get(coin);
        coins.put(coin, numberOfCoins);
    }

    public Map getCoins() {
        return coins;
    }

    public Map getLoot() {
        return loot;
    }

    public Map getGems() { return gems; }

    public Map getArt() { return art;}

    public void deleteAll() {
        coins.clear();
        loot.clear();
        gems.clear();
        art.clear();
    }


}