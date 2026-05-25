package com.dante.paul.dd5erandomlootgenerator.Tracker;

import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemRarity;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemTheme;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.TierOfPlay;

import org.json.JSONException;
import org.json.JSONObject;

public class AwardedItem {

    public final String name;
    public final TierOfPlay tier;
    public final MagicItemRarity rarity;
    public final MagicItemTheme theme;
    public final long commitTimestamp;
    public final String commitId;
    public boolean crossedOut;

    public AwardedItem(String name,
                       TierOfPlay tier,
                       MagicItemRarity rarity,
                       MagicItemTheme theme,
                       long commitTimestamp,
                       String commitId) {
        this(name, tier, rarity, theme, commitTimestamp, commitId, false);
    }

    public AwardedItem(String name,
                       TierOfPlay tier,
                       MagicItemRarity rarity,
                       MagicItemTheme theme,
                       long commitTimestamp,
                       String commitId,
                       boolean crossedOut) {
        this.name = name;
        this.tier = tier;
        this.rarity = rarity;
        this.theme = theme;
        this.commitTimestamp = commitTimestamp;
        this.commitId = commitId;
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
        boolean crossedOut = obj.optBoolean("crossedOut", false);
        return new AwardedItem(name, tier, rarity, theme, ts, commitId, crossedOut);
    }

    private static <E extends Enum<E>> E enumByOrdinal(E[] values, int ordinal, E fallback) {
        if (ordinal < 0 || ordinal >= values.length) return fallback;
        return values[ordinal];
    }
}
