package com.dante.paul.dd5erandomlootgenerator.Tracker;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemRarity;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemTheme;
import com.dante.paul.dd5erandomlootgenerator.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awarded_items);

        Toolbar toolbar = findViewById(R.id.awarded_items_toolbar);
        setSupportActionBar(toolbar);

        String campaignId = getIntent().getStringExtra(EXTRA_CAMPAIGN_ID);
        CampaignStore store = new CampaignStore(this);
        Campaign campaign = null;
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
        return tv;
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
