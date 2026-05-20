package com.dante.paul.dd5erandomlootgenerator.Tracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CampaignStore {

    private static final String TAG = "CampaignStore";
    private static final String PREFS = "LootGenCampaigns";
    private static final String KEY_IDS = "campaign_ids";
    private static final String KEY_ACTIVE = "active_campaign_id";
    private static final String KEY_CAMPAIGN_PREFIX = "campaign.";
    private static final String DEFAULT_NAME = "Default Campaign";

    private final Context appContext;

    public CampaignStore(@NonNull Context context) {
        this.appContext = context.getApplicationContext();
        ensureAtLeastOneCampaign();
    }

    public List<Campaign> listCampaigns() {
        List<Campaign> result = new ArrayList<>();
        for (String id : listIds()) {
            Campaign c = loadCampaign(id);
            if (c != null) result.add(c);
        }
        return result;
    }

    @NonNull
    public Campaign getActive() {
        SharedPreferences prefs = prefs();
        String activeId = prefs.getString(KEY_ACTIVE, null);
        if (activeId != null) {
            Campaign c = loadCampaign(activeId);
            if (c != null) return c;
        }
        List<String> ids = listIds();
        if (!ids.isEmpty()) {
            Campaign c = loadCampaign(ids.get(0));
            if (c != null) {
                prefs.edit().putString(KEY_ACTIVE, c.getId()).apply();
                return c;
            }
        }
        // Should not happen because ensureAtLeastOneCampaign ran in the ctor,
        // but if storage was wiped concurrently, create one.
        Campaign created = createCampaign(DEFAULT_NAME);
        prefs.edit().putString(KEY_ACTIVE, created.getId()).apply();
        return created;
    }

    public void setActive(String id) {
        prefs().edit().putString(KEY_ACTIVE, id).apply();
    }

    public Campaign createCampaign(@NonNull String name) {
        String id = UUID.randomUUID().toString();
        Campaign c = new Campaign(id, name.trim().isEmpty() ? DEFAULT_NAME : name.trim());
        saveCampaign(c);
        List<String> ids = listIds();
        ids.add(id);
        writeIds(ids);
        SharedPreferences prefs = prefs();
        if (!prefs.contains(KEY_ACTIVE)) {
            prefs.edit().putString(KEY_ACTIVE, id).apply();
        }
        return c;
    }

    public void saveCampaign(@NonNull Campaign campaign) {
        try {
            String json = campaign.toJson().toString();
            prefs().edit().putString(KEY_CAMPAIGN_PREFIX + campaign.getId(), json).apply();
        } catch (JSONException e) {
            Log.w(TAG, "Failed to serialize campaign " + campaign.getId(), e);
        }
    }

    public void renameCampaign(@NonNull String id, @NonNull String newName) {
        Campaign c = loadCampaign(id);
        if (c == null) return;
        c.setName(newName);
        saveCampaign(c);
    }

    public void deleteCampaign(@NonNull String id) {
        SharedPreferences prefs = prefs();
        prefs.edit().remove(KEY_CAMPAIGN_PREFIX + id).apply();
        List<String> ids = listIds();
        ids.remove(id);
        writeIds(ids);
        if (id.equals(prefs.getString(KEY_ACTIVE, null))) {
            prefs.edit().putString(KEY_ACTIVE, ids.isEmpty() ? null : ids.get(0)).apply();
        }
        ensureAtLeastOneCampaign();
    }

    public void resetCampaign(@NonNull String id) {
        Campaign c = loadCampaign(id);
        if (c == null) return;
        c.resetCounts();
        saveCampaign(c);
    }

    @Nullable
    private Campaign loadCampaign(@NonNull String id) {
        String json = prefs().getString(KEY_CAMPAIGN_PREFIX + id, null);
        if (json == null) return null;
        try {
            return Campaign.fromJson(new JSONObject(json));
        } catch (JSONException e) {
            Log.w(TAG, "Failed to parse campaign " + id, e);
            return null;
        }
    }

    private List<String> listIds() {
        List<String> result = new ArrayList<>();
        String raw = prefs().getString(KEY_IDS, null);
        if (raw == null) return result;
        try {
            JSONArray arr = new JSONArray(raw);
            for (int i = 0; i < arr.length(); i++) {
                result.add(arr.getString(i));
            }
        } catch (JSONException e) {
            Log.w(TAG, "Failed to parse campaign id list", e);
        }
        return result;
    }

    private void writeIds(List<String> ids) {
        JSONArray arr = new JSONArray();
        for (String id : ids) arr.put(id);
        prefs().edit().putString(KEY_IDS, arr.toString()).apply();
    }

    private void ensureAtLeastOneCampaign() {
        if (listIds().isEmpty()) {
            createCampaign(DEFAULT_NAME);
        }
    }

    private SharedPreferences prefs() {
        return appContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
}
