package com.verdantsoftware.lootforge.Tracker;

import com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemRarity;
import com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemTheme;
import com.verdantsoftware.lootforge.EnumeratedClasses.TierOfPlay;

import org.json.JSONException;
import org.json.JSONObject;

public class AwardedItem {

    public final String name;
    public final TierOfPlay tier;
    public final MagicItemRarity rarity;
    public final MagicItemTheme theme;
    public final long commitTimestamp;
    public final String commitId;
    /**
     * Campaign session number at the time this item was committed.
     * 0 means "unknown" — applies to items committed before session
     * tracking was added (deserialized from older save files).
     */
    public final int sessionNumber;
    public boolean crossedOut;

    public AwardedItem(String name,
                       TierOfPlay tier,
                       MagicItemRarity rarity,
                       MagicItemTheme theme,
                       long commitTimestamp,
                       String commitId,
                       int sessionNumber) {
        this(name, tier, rarity, theme, commitTimestamp, commitId, sessionNumber, false);
    }

    public AwardedItem(String name,
                       TierOfPlay tier,
                       MagicItemRarity rarity,
                       MagicItemTheme theme,
                       long commitTimestamp,
                       String commitId,
                       int sessionNumber,
                       boolean crossedOut) {
        this.name = name;
        this.tier = tier;
        this.rarity = rarity;
        this.theme = theme;
        this.commitTimestamp = commitTimestamp;
        this.commitId = commitId;
        this.sessionNumber = sessionNumber;
        this.crossedOut = crossedOut;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("tier", tier.ordinal());
        obj.put("rarity", rarity.ordinal());
        obj.put("theme", theme == null ? -1 : theme.ordinal());
        obj.put("commitTimestamp", commitTimestamp);
        obj.put("commitId", commitId);
        obj.put("sessionNumber", sessionNumber);
        obj.put("crossedOut", crossedOut);
        return obj;
    }

    public static AwardedItem fromJson(JSONObject obj) throws JSONException {
        String name = obj.optString("name", "");
        TierOfPlay tier = enumByOrdinal(TierOfPlay.values(), obj.optInt("tier", 0), TierOfPlay.TIER_1);
        MagicItemRarity rarity = enumByOrdinal(MagicItemRarity.values(),
                obj.optInt("rarity", 0), MagicItemRarity.COMMON);
        int themeOrdinal = obj.optInt("theme", -1);
        MagicItemTheme theme = themeOrdinal < 0
                ? null
                : enumByOrdinal(MagicItemTheme.values(), themeOrdinal, MagicItemTheme.RANDOM);
        long ts = obj.optLong("commitTimestamp", 0);
        String commitId = obj.optString("commitId", "");
        int sessionNumber = obj.optInt("sessionNumber", 0);
        boolean crossedOut = obj.optBoolean("crossedOut", false);
        return new AwardedItem(name, tier, rarity, theme, ts, commitId, sessionNumber, crossedOut);
    }

    private static <E extends Enum<E>> E enumByOrdinal(E[] values, int ordinal, E fallback) {
        if (ordinal < 0 || ordinal >= values.length) return fallback;
        return values[ordinal];
    }
}
