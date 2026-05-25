package com.dante.paul.dd5erandomlootgenerator.Tracker;

import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemRarity;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.TierOfPlay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Campaign {

    private static final int TIERS = 4;
    private static final int RARITIES = 5;

    private final String id;
    private String name;
    private final long createdAt;
    private long lastUpdated;
    private final int[][] counts;
    private final List<AwardedItem> awardedItems;
    private boolean suppressOverMaxWarning;

    public Campaign(String id, String name) {
        this.id = id;
        this.name = name;
        this.createdAt = System.currentTimeMillis();
        this.lastUpdated = this.createdAt;
        this.counts = new int[TIERS][RARITIES];
        this.awardedItems = new ArrayList<>();
        this.suppressOverMaxWarning = false;
    }

    private Campaign(String id,
                     String name,
                     long createdAt,
                     long lastUpdated,
                     int[][] counts,
                     List<AwardedItem> awardedItems,
                     boolean suppressOverMaxWarning) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.counts = counts;
        this.awardedItems = awardedItems;
        this.suppressOverMaxWarning = suppressOverMaxWarning;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public long getCreatedAt() { return createdAt; }
    public long getLastUpdated() { return lastUpdated; }

    public void setName(String name) {
        this.name = name;
        touch();
    }

    public int getCount(TierOfPlay tier, MagicItemRarity rarity) {
        return counts[tier.ordinal()][rarity.ordinal()];
    }

    public void setCount(TierOfPlay tier, MagicItemRarity rarity, int value) {
        counts[tier.ordinal()][rarity.ordinal()] = Math.max(0, value);
        touch();
    }

    public void increment(TierOfPlay tier, MagicItemRarity rarity) {
        counts[tier.ordinal()][rarity.ordinal()]++;
        touch();
    }

    public void decrement(TierOfPlay tier, MagicItemRarity rarity) {
        int[] row = counts[tier.ordinal()];
        if (row[rarity.ordinal()] > 0) row[rarity.ordinal()]--;
        touch();
    }

    public void resetCounts() {
        for (int t = 0; t < TIERS; t++) {
            for (int r = 0; r < RARITIES; r++) {
                counts[t][r] = 0;
            }
        }
        awardedItems.clear();
        touch();
    }

    public List<AwardedItem> getAwardedItems() {
        return Collections.unmodifiableList(awardedItems);
    }

    public void addAwardedItems(List<AwardedItem> items) {
        for (AwardedItem item : items) {
            awardedItems.add(item);
            counts[item.tier.ordinal()][item.rarity.ordinal()]++;
        }
        touch();
    }

    public boolean isSuppressOverMaxWarning() { return suppressOverMaxWarning; }

    public void setSuppressOverMaxWarning(boolean value) {
        this.suppressOverMaxWarning = value;
        touch();
    }

    public int totalCount() {
        int sum = 0;
        for (int t = 0; t < TIERS; t++) {
            for (int r = 0; r < RARITIES; r++) {
                sum += counts[t][r];
            }
        }
        return sum;
    }

    private void touch() {
        lastUpdated = System.currentTimeMillis();
    }

    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("name", name);
        obj.put("createdAt", createdAt);
        obj.put("lastUpdated", lastUpdated);
        StringBuilder flat = new StringBuilder();
        for (int t = 0; t < TIERS; t++) {
            for (int r = 0; r < RARITIES; r++) {
                if (flat.length() > 0) flat.append(',');
                flat.append(counts[t][r]);
            }
        }
        obj.put("counts", flat.toString());
        JSONArray itemsArr = new JSONArray();
        for (AwardedItem item : awardedItems) {
            itemsArr.put(item.toJson());
        }
        obj.put("awardedItems", itemsArr);
        obj.put("suppressOverMaxWarning", suppressOverMaxWarning);
        return obj;
    }

    public static Campaign fromJson(JSONObject obj) throws JSONException {
        String id = obj.getString("id");
        String name = obj.getString("name");
        long createdAt = obj.optLong("createdAt", System.currentTimeMillis());
        long lastUpdated = obj.optLong("lastUpdated", createdAt);
        int[][] counts = new int[TIERS][RARITIES];
        String flat = obj.optString("counts", "");
        if (!flat.isEmpty()) {
            String[] parts = flat.split(",");
            int idx = 0;
            for (int t = 0; t < TIERS && idx < parts.length; t++) {
                for (int r = 0; r < RARITIES && idx < parts.length; r++) {
                    try {
                        counts[t][r] = Integer.parseInt(parts[idx].trim());
                    } catch (NumberFormatException ignored) {
                        counts[t][r] = 0;
                    }
                    idx++;
                }
            }
        }
        List<AwardedItem> awardedItems = new ArrayList<>();
        JSONArray itemsArr = obj.optJSONArray("awardedItems");
        if (itemsArr != null) {
            for (int i = 0; i < itemsArr.length(); i++) {
                awardedItems.add(AwardedItem.fromJson(itemsArr.getJSONObject(i)));
            }
        }
        boolean suppressOverMaxWarning = obj.optBoolean("suppressOverMaxWarning", false);
        return new Campaign(id, name, createdAt, lastUpdated, counts, awardedItems, suppressOverMaxWarning);
    }

    public static int targetForTierAndRarity(TierOfPlay tier, MagicItemRarity rarity) {
        // From the DMG's Magic Items Awarded by Level table.
        switch (tier) {
            case TIER_1:
                switch (rarity) {
                    case COMMON: return 6;
                    case UNCOMMON: return 4;
                    case RARE: return 1;
                    default: return 0;
                }
            case TIER_2:
                switch (rarity) {
                    case COMMON: return 10;
                    case UNCOMMON: return 17;
                    case RARE: return 6;
                    case VERY_RARE: return 1;
                    default: return 0;
                }
            case TIER_3:
                switch (rarity) {
                    case COMMON: return 3;
                    case UNCOMMON: return 7;
                    case RARE: return 11;
                    case VERY_RARE: return 7;
                    case LEGENDARY: return 2;
                }
                return 0;
            case TIER_4:
            default:
                switch (rarity) {
                    case RARE: return 5;
                    case VERY_RARE: return 11;
                    case LEGENDARY: return 9;
                    default: return 0;
                }
        }
    }
}
