package com.verdantsoftware.lootforge;

import android.content.Context;
import android.net.Uri;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Looks up dnd5e.wikidot.com URLs for magic-item names.
 *
 * <p>At build time (see the matcher script) we cross-reference every item
 * name that appears in the app's tables against wikidot's item index and
 * bake the resulting name→URL map into {@code assets/wikidot_urls.json}.
 * About 95% of items hit a direct match. The remainder fall through to a
 * site-search URL so the user lands on a results page instead of a dead
 * end.
 *
 * <p>Thread-safe: the asset is loaded once and held in an immutable map.
 */
public final class WikidotUrls {

    private static final String ASSET = "wikidot_urls.json";
    private static final String SEARCH_URL =
            "https://dnd5e.wikidot.com/search:site/q/";
    private static final String WIKIDOT_ROOT = "https://dnd5e.wikidot.com";

    /**
     * Regex → direct wikidot URL, applied before falling back to site search.
     * Used to resolve runtime-composed item names (e.g. "Armor of fire
     * resistance (leather)" / "Potion of supreme healing") that aren't
     * literal keys in {@link #ASSET}.
     *
     * <p>{@link LinkedHashMap} preserves declaration order — first match wins.
     */
    private static final Map<Pattern, String> DYNAMIC_PATTERNS = new LinkedHashMap<>();
    static {
        // "Armor of <damageType> resistance" with optional "(<armorType>)" suffix
        DYNAMIC_PATTERNS.put(
                Pattern.compile("(?i)^armor of \\w+ resistance(\\s*\\([^)]+\\))?$"),
                WIKIDOT_ROOT + "/wondrous-items:armor-of-resistance");
        // "Ring of <damageType> resistance"
        DYNAMIC_PATTERNS.put(
                Pattern.compile("(?i)^ring of \\w+ resistance$"),
                WIKIDOT_ROOT + "/wondrous-items:ring-of-resistance");
        // "Potion of <damageType> resistance"
        DYNAMIC_PATTERNS.put(
                Pattern.compile("(?i)^potion of \\w+ resistance$"),
                WIKIDOT_ROOT + "/wondrous-items:potion-of-resistance");
        // "Potion of (greater|superior|supreme) healing"
        DYNAMIC_PATTERNS.put(
                Pattern.compile("(?i)^potion of (greater|superior|supreme) healing$"),
                WIKIDOT_ROOT + "/wondrous-items:potion-of-healing");
    }

    private static volatile Map<String, String> cache;

    /**
     * @return true when {@link #urlFor} will return a direct wikidot item
     *         page for {@code itemName}, false when it will fall back to a
     *         site-search URL. UI uses this to de-emphasize the link icon
     *         for items that aren't (yet) on wikidot.
     */
    public static boolean hasDirectMatch(Context context, String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) return false;
        Map<String, String> map = ensureLoaded(context);
        if (map.get(itemName) != null) return true;
        int paren = itemName.lastIndexOf('(');
        if (paren > 0) {
            String trimmed = itemName.substring(0, paren).trim();
            if (map.get(trimmed) != null) return true;
        }
        for (Pattern p : DYNAMIC_PATTERNS.keySet()) {
            if (p.matcher(itemName.trim()).matches()) return true;
        }
        return false;
    }

    private WikidotUrls() {}

    /**
     * Resolve an item name to a wikidot URL. Returns a direct item page
     * when known, otherwise a wikidot site-search URL for the item name.
     * Never returns null.
     */
    public static String urlFor(Context context, String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) {
            return "https://dnd5e.wikidot.com/";
        }
        Map<String, String> map = ensureLoaded(context);
        String direct = map.get(itemName);
        if (direct != null) return direct;
        // Try trimming a trailing "(theme — rarity)" suffix in case the
        // caller passed a display label rather than the raw name.
        int paren = itemName.lastIndexOf('(');
        if (paren > 0) {
            String trimmed = itemName.substring(0, paren).trim();
            direct = map.get(trimmed);
            if (direct != null) return direct;
        }
        // Pattern-based fallback for runtime-composed names (e.g. damage-type
        // resistance variants and tiered healing potions) that aren't literal
        // keys in the bundled JSON.
        for (Map.Entry<Pattern, String> entry : DYNAMIC_PATTERNS.entrySet()) {
            if (entry.getKey().matcher(itemName.trim()).matches()) {
                return entry.getValue();
            }
        }
        return SEARCH_URL + Uri.encode(itemName);
    }

    private static Map<String, String> ensureLoaded(Context context) {
        Map<String, String> local = cache;
        if (local != null) return local;
        synchronized (WikidotUrls.class) {
            if (cache != null) return cache;
            cache = load(context.getApplicationContext());
            return cache;
        }
    }

    private static Map<String, String> load(Context appContext) {
        Map<String, String> out = new HashMap<>();
        try (InputStream in = appContext.getAssets().open(ASSET);
             BufferedReader r = new BufferedReader(
                     new InputStreamReader(in, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) sb.append(line);
            JSONObject obj = new JSONObject(sb.toString());
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String k = keys.next();
                out.put(k, obj.optString(k, null));
            }
        } catch (IOException | org.json.JSONException ignored) {
            // On any failure we just return an empty map; urlFor() will
            // fall back to site-search URLs for everything.
        }
        return out;
    }
}
