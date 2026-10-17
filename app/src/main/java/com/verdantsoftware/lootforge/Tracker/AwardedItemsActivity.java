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
import android.widget.RadioGroup;
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
import com.verdantsoftware.lootforge.WikidotUrls;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        RadioGroup grouping = findViewById(R.id.awarded_items_group_choices);
        grouping.setOnCheckedChangeListener((g, checkedId) -> {
            container.removeAllViews();
            boolean bySession = checkedId == R.id.awarded_items_group_session;
            populate(container, campaign, bySession);
        });
        // Initial render uses the default (By Session — matches the
        // android:checked="true" set on awarded_items_group_session).
        populate(container, campaign, true);
    }

    private void populate(LinearLayout container, Campaign campaign, boolean bySession) {
        List<AwardedItem> items = new ArrayList<>(campaign.getAwardedItems());
        if (items.isEmpty()) {
            container.addView(emptyView());
            return;
        }
        // Sort by timestamp descending so the most recent group appears first.
        Collections.sort(items, new Comparator<AwardedItem>() {
            @Override
            public int compare(AwardedItem a, AwardedItem b) {
                return Long.compare(b.commitTimestamp, a.commitTimestamp);
            }
        });

        if (bySession) {
            populateBySession(container, items);
        } else {
            populateByCommit(container, items);
        }
    }

    private void populateByCommit(LinearLayout container, List<AwardedItem> items) {
        Map<String, List<AwardedItem>> groups = new LinkedHashMap<>();
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
                container.addView(itemRowWithLink(item));
            }
        }
    }

    /**
     * Groups awarded items by their {@link AwardedItem#sessionNumber}.
     * Renders every session from the campaign's current session down to 1,
     * so the user can see which sessions were "dry" — empty sessions get a
     * "No items given" placeholder under their header. Items with no session
     * (legacy data) appear under a "No session" group at the very bottom.
     */
    private void populateBySession(LinearLayout container, List<AwardedItem> items) {
        Map<Integer, List<AwardedItem>> groups = new java.util.HashMap<>();
        List<AwardedItem> unknown = new ArrayList<>();
        int maxSessionWithItems = 0;
        for (AwardedItem item : items) {
            if (item.sessionNumber <= 0) {
                unknown.add(item);
                continue;
            }
            List<AwardedItem> list = groups.get(item.sessionNumber);
            if (list == null) {
                list = new ArrayList<>();
                groups.put(item.sessionNumber, list);
            }
            list.add(item);
            if (item.sessionNumber > maxSessionWithItems) {
                maxSessionWithItems = item.sessionNumber;
            }
        }
        // Show every session from 1..max(currentSession, maxSessionWithItems),
        // newest first, including empties.
        int top = Math.max(campaign.getCurrentSession(), maxSessionWithItems);
        boolean first = true;
        for (int s = top; s >= 1; s--) {
            if (!first) container.addView(spacer(dp(12)));
            first = false;
            container.addView(groupHeader(getString(R.string.session_label_short, s)));
            List<AwardedItem> sessionItems = groups.get(s);
            if (sessionItems == null || sessionItems.isEmpty()) {
                container.addView(emptySessionRow());
            } else {
                for (AwardedItem item : sessionItems) {
                    container.addView(itemRowWithLink(item));
                }
            }
        }
        // Legacy items (sessionNumber == 0) at the bottom.
        if (!unknown.isEmpty()) {
            if (!first) container.addView(spacer(dp(12)));
            container.addView(groupHeader(getString(R.string.session_label_unknown)));
            for (AwardedItem item : unknown) {
                container.addView(itemRowWithLink(item));
            }
        }
    }

    private TextView emptySessionRow() {
        TextView tv = new TextView(this);
        tv.setText(getString(R.string.awarded_items_session_empty));
        tv.setTextColor(Color.GRAY);
        tv.setTextSize(16);
        tv.setTypeface(tv.getTypeface(), android.graphics.Typeface.ITALIC);
        tv.setPadding(dp(16), dp(4), 0, dp(4));
        return tv;
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
        String sessionTag = item.sessionNumber > 0
                ? "  · " + getString(R.string.session_label_short, item.sessionNumber)
                : "";
        tv.setText("•  " + item.name + "  (" + suffix + ")" + sessionTag);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(18);
        tv.setPadding(dp(8), dp(4), 0, dp(4));
        applyStrikethrough(tv, item.crossedOut);
        tv.setOnClickListener(v -> onItemTapped(item, tv));
        return tv;
    }

    /**
     * Wraps {@link #itemRow(AwardedItem)} in a horizontal row that also
     * shows a wikidot-link ⓘ icon at the right edge. The original TextView
     * keeps its strikethrough-on-tap behavior; only the icon launches the
     * browser.
     */
    private View itemRowWithLink(AwardedItem item) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);

        TextView tv = itemRow(item);
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        row.addView(tv, tvParams);

        TextView info = new TextView(this);
        info.setText("ⓘ");
        info.setTextSize(20);
        final String itemName = item.name;
        // Blue when a direct wikidot page exists; grey when we'll fall back
        // to site search (likely no results today, but still tappable in
        // case the item is added to the wiki later).
        boolean direct = WikidotUrls.hasDirectMatch(this, itemName);
        info.setTextColor(direct ? 0xFF1565C0 : 0xFF9E9E9E);
        info.setPadding(dp(12), dp(4), dp(8), dp(4));
        info.setOnClickListener(v -> openWikidot(itemName));
        row.addView(info);
        return row;
    }

    private void openWikidot(String itemName) {
        String url = WikidotUrls.urlFor(this, itemName);
        try {
            android.content.Intent intent = new android.content.Intent(
                    android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url));
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(this, R.string.no_browser_app, Toast.LENGTH_SHORT).show();
        }
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
