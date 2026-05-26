package com.verdantsoftware.lootforge.Tracker;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.verdantsoftware.lootforge.EnumeratedClasses.TierOfPlay;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemRarity;
import com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemTheme;
import com.verdantsoftware.lootforge.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AwardedItemsActivity extends AppCompatActivity {

    public static final String EXTRA_CAMPAIGN_ID = "campaign_id";

    private CampaignStore store;
    private Campaign campaign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awarded_items);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.awarded_items_root), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        Toolbar toolbar = findViewById(R.id.awarded_items_toolbar);
        setSupportActionBar(toolbar);

        String campaignId = getIntent().getStringExtra(EXTRA_CAMPAIGN_ID);
        store = new CampaignStore(this);
        campaign = null;
        if (campaignId != null) {
            for (Campaign c : store.listCampaigns()) {
                if (campaignId.equals(c.getId())) { campaign = c; break; }
            }
        }
        if (campaign == null) campaign = store.getActive();

        setTitle(getString(R.string.awarded_items_title) + " — " + campaign.getName());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        LinearLayout container = findViewById(R.id.awarded_items_container);
        populate(container, campaign);
    }

    private void populate(LinearLayout container, Campaign campaign) {
        List<AwardedItem> items = new ArrayList<>(campaign.getAwardedItems());
        if (items.isEmpty()) {
            container.addView(emptyView());
            return;
        }

        Map<String, List<AwardedItem>> groups = new LinkedHashMap<>();
        // Sort by timestamp descending so most-recent commit groups appear first.
        Collections.sort(items, new Comparator<AwardedItem>() {
            @Override
            public int compare(AwardedItem a, AwardedItem b) {
                return Long.compare(b.commitTimestamp, a.commitTimestamp);
            }
        });
        for (AwardedItem item : items) {
            String key = item.commitId == null || item.commitId.isEmpty()
                    ? String.valueOf(item.commitTimestamp) : item.commitId;
            List<AwardedItem> list = groups.get(key);
            if (list == null) {
                list = new ArrayList<>();
                groups.put(key, list);
            }
            list.add(item);
        }

        DateFormat fmt = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
        boolean first = true;
        for (Map.Entry<String, List<AwardedItem>> entry : groups.entrySet()) {
            if (!first) container.addView(spacer(dp(12)));
            first = false;
            container.addView(groupHeader(fmt.format(new Date(entry.getValue().get(0).commitTimestamp))));
            for (AwardedItem item : entry.getValue()) {
                container.addView(itemRow(item));
            }
        }
    }

    private TextView groupHeader(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(20);
        tv.setTypeface(tv.getTypeface(), android.graphics.Typeface.BOLD);
        tv.setPadding(0, dp(8), 0, dp(4));
        return tv;
    }

    private TextView itemRow(AwardedItem item) {
        TextView tv = new TextView(this);
        String suffix = themeLabel(item.theme) + " — " + rarityLabel(item.rarity);
        tv.setText("•  " + item.name + "  (" + suffix + ")");
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(18);
        tv.setPadding(dp(8), dp(4), 0, dp(4));
        applyStrikethrough(tv, item.crossedOut);
        tv.setOnClickListener(v -> onItemTapped(item, tv));
        return tv;
    }

    private void applyStrikethrough(TextView tv, boolean on) {
        int flags = tv.getPaintFlags();
        if (on) {
            tv.setPaintFlags(flags | Paint.STRIKE_THRU_TEXT_FLAG);
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setPaintFlags(flags & ~Paint.STRIKE_THRU_TEXT_FLAG);
            tv.setTextColor(Color.BLACK);
        }
    }

    private void onItemTapped(AwardedItem item, TextView tv) {
        if (item.rarity == null) return;
        if (!item.crossedOut) {
            // Crossing out — pick a tier whose count for this rarity we'll decrement.
            List<TierOfPlay> eligible = new ArrayList<>();
            for (TierOfPlay t : TierOfPlay.values()) {
                if (Campaign.targetForTierAndRarity(t, item.rarity) > 0
                        && campaign.getCount(t, item.rarity) > 0) {
                    eligible.add(t);
                }
            }
            if (eligible.isEmpty()) {
                item.crossedOut = true;
                store.saveCampaign(campaign);
                applyStrikethrough(tv, true);
                return;
            }
            if (eligible.size() == 1) {
                TierOfPlay only = eligible.get(0);
                campaign.decrement(only, item.rarity);
                item.crossedOut = true;
                store.saveCampaign(campaign);
                applyStrikethrough(tv, true);
                return;
            }
            showTierPicker("Remove one " + rarityLabel(item.rarity) + " from which tier?",
                    eligible, item.rarity, chosen -> {
                campaign.decrement(chosen, item.rarity);
                item.crossedOut = true;
                store.saveCampaign(campaign);
                applyStrikethrough(tv, true);
            });
        } else {
            // Uncrossing — pick a tier to credit.
            List<TierOfPlay> eligible = new ArrayList<>();
            for (TierOfPlay t : TierOfPlay.values()) {
                if (Campaign.targetForTierAndRarity(t, item.rarity) > 0) {
                    eligible.add(t);
                }
            }
            showTierPicker("Add one " + rarityLabel(item.rarity) + " to which tier?",
                    eligible, item.rarity, chosen -> {
                campaign.increment(chosen, item.rarity);
                item.crossedOut = false;
                store.saveCampaign(campaign);
                applyStrikethrough(tv, false);
            });
        }
    }

    private interface TierChosen { void run(TierOfPlay tier); }

    private void showTierPicker(String title,
                                List<TierOfPlay> tiers,
                                com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemRarity rarity,
                                TierChosen onChosen) {
        List<String> labels = new ArrayList<>();
        for (TierOfPlay t : tiers) {
            int current = campaign.getCount(t, rarity);
            int target = Campaign.targetForTierAndRarity(t, rarity);
            labels.add(tierLabel(t) + " — " + current + " / " + target);
        }
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        int hPad = dp(24);
        container.setPadding(hPad, dp(16), hPad, 0);

        Spinner spinner = new Spinner(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinner, labels);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        container.addView(spinner);

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(container)
                .setPositiveButton(android.R.string.ok, (d, w) -> {
                    int idx = spinner.getSelectedItemPosition();
                    if (idx >= 0 && idx < tiers.size()) onChosen.run(tiers.get(idx));
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private static String tierLabel(TierOfPlay tier) {
        switch (tier) {
            case TIER_1: return "Tier 1 (1–4)";
            case TIER_2: return "Tier 2 (5–10)";
            case TIER_3: return "Tier 3 (11–16)";
            case TIER_4: return "Tier 4 (17–20)";
            default: return tier.name();
        }
    }

    private TextView emptyView() {
        TextView tv = new TextView(this);
        tv.setText(R.string.awarded_items_empty);
        tv.setTextSize(20);
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(0, dp(32), 0, 0);
        return tv;
    }

    private LinearLayout spacer(int heightPx) {
        LinearLayout s = new LinearLayout(this);
        s.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, heightPx));
        return s;
    }

    private int dp(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String rarityLabel(MagicItemRarity rarity) {
        if (rarity == null) return "";
        switch (rarity) {
            case COMMON: return getString(R.string.tracker_rarity_common);
            case UNCOMMON: return getString(R.string.tracker_rarity_uncommon);
            case RARE: return getString(R.string.tracker_rarity_rare);
            case VERY_RARE: return getString(R.string.tracker_rarity_very_rare);
            case LEGENDARY: default: return getString(R.string.tracker_rarity_legendary);
        }
    }

    private String themeLabel(MagicItemTheme theme) {
        if (theme == null) return "Random";
        switch (theme) {
            case ARCANA: return "Arcana";
            case ARMAMENTS: return "Armaments";
            case IMPLEMENTS: return "Implements";
            case RELICS: return "Relics";
            default: return "Random";
        }
    }

    public static android.content.Intent intent(Context context, String campaignId) {
        android.content.Intent i = new android.content.Intent(context, AwardedItemsActivity.class);
        i.putExtra(EXTRA_CAMPAIGN_ID, campaignId);
        return i;
    }
}
