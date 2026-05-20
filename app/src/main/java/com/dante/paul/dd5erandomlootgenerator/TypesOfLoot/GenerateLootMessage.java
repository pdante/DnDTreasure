package com.dante.paul.dd5erandomlootgenerator.TypesOfLoot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemRarity;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemTheme;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.TierOfPlay;
import com.dante.paul.dd5erandomlootgenerator.R;
import com.dante.paul.dd5erandomlootgenerator.Tracker.AwardedItem;
import com.dante.paul.dd5erandomlootgenerator.Tracker.Campaign;
import com.dante.paul.dd5erandomlootgenerator.Tracker.CampaignStore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GenerateLootMessage extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        final String lootMessage = args.getString("loot", "");
        final String lootTitle = args.getString("loot_summary", "");
        final String[] itemNames = args.getStringArray("item_names");
        final int[] itemRarities = args.getIntArray("item_rarities");
        final int[] itemThemes = args.getIntArray("item_themes");
        int partyTierOrdinal = args.getInt("party_tier", -1);
        final boolean has2024Items =
                itemNames != null && itemNames.length > 0 && partyTierOrdinal >= 0;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(lootTitle);

        final String prefixText = has2024Items ? extractNonItemsPrefix(lootMessage) : "";
        final CheckBox[] checks = has2024Items
                ? new CheckBox[itemNames.length]
                : null;

        if (has2024Items) {
            builder.setView(buildItemsView(prefixText, itemNames, itemRarities, itemThemes, checks));
            final CampaignStore store = new CampaignStore(getActivity());
            final Campaign active = store.getActive();
            final TierOfPlay tier = TierOfPlay.values()[partyTierOrdinal];
            builder.setPositiveButton(R.string.copy_and_commit_to_campaign, (dialog, which) -> {
                String text = buildFilteredLootText(prefixText, itemNames, itemRarities, itemThemes, checks);
                copyToClipboard(text);
                List<AwardedItem> built = buildCheckedAwardedItems(
                        itemNames, itemRarities, itemThemes, checks, tier);
                if (!built.isEmpty()) {
                    active.addAwardedItems(built);
                    store.saveCampaign(active);
                }
                Toast.makeText(getActivity(), R.string.commit_to_campaign_done, Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Copy and Dismiss", (dialog, which) -> {
                String text = buildFilteredLootText(prefixText, itemNames, itemRarities, itemThemes, checks);
                copyToClipboard(text);
            });
        } else {
            builder.setMessage(lootMessage);
            builder.setNegativeButton("Copy and Dismiss", (dialog, which) -> copyToClipboard(lootMessage));
        }

        builder.setNeutralButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());

        return builder.create();
    }

    private ScrollView buildItemsView(String prefix,
                                      String[] names,
                                      int[] rarities,
                                      int[] themes,
                                      CheckBox[] checks) {
        LinearLayout container = new LinearLayout(getActivity());
        container.setOrientation(LinearLayout.VERTICAL);
        int hPad = dp(24);
        container.setPadding(hPad, dp(8), hPad, 0);

        if (!prefix.trim().isEmpty()) {
            TextView prefixView = new TextView(getActivity());
            prefixView.setText(prefix.trim());
            prefixView.setTextSize(16);
            prefixView.setGravity(Gravity.CENTER_HORIZONTAL);
            prefixView.setPadding(0, 0, 0, dp(8));
            container.addView(prefixView);
        }

        TextView header = new TextView(getActivity());
        header.setText("Items:");
        header.setTextSize(16);
        header.setTypeface(header.getTypeface(), Typeface.BOLD);
        header.setPadding(0, dp(4), 0, dp(4));
        container.addView(header);

        for (int i = 0; i < names.length; i++) {
            CheckBox cb = new CheckBox(getActivity());
            cb.setText(formatItemLabel(names[i], rarities[i], themes[i]));
            cb.setChecked(true);
            cb.setTextSize(16);
            checks[i] = cb;
            container.addView(cb);
        }

        ScrollView scroll = new ScrollView(getActivity());
        scroll.addView(container, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return scroll;
    }

    private static String extractNonItemsPrefix(String fullLoot) {
        int idx = fullLoot.indexOf("Items:");
        if (idx < 0) return "";
        return fullLoot.substring(0, idx);
    }

    private static String buildFilteredLootText(String prefix,
                                                String[] names,
                                                int[] rarities,
                                                int[] themes,
                                                CheckBox[] checks) {
        StringBuilder sb = new StringBuilder();
        String trimmedPrefix = prefix.trim();
        if (!trimmedPrefix.isEmpty()) {
            sb.append(trimmedPrefix).append("\r\n");
        }
        boolean anyChecked = false;
        for (int i = 0; i < names.length; i++) {
            if (checks[i] != null && checks[i].isChecked()) {
                if (!anyChecked) {
                    sb.append("Items:\r\n");
                    anyChecked = true;
                }
                sb.append(formatItemLabel(names[i], rarities[i], themes[i])).append("\r\n");
            }
        }
        return sb.toString();
    }

    private static List<AwardedItem> buildCheckedAwardedItems(String[] names,
                                                              int[] rarities,
                                                              int[] themes,
                                                              CheckBox[] checks,
                                                              TierOfPlay tier) {
        List<AwardedItem> out = new ArrayList<>();
        long ts = System.currentTimeMillis();
        String commitId = UUID.randomUUID().toString();
        MagicItemRarity[] rarityValues = MagicItemRarity.values();
        MagicItemTheme[] themeValues = MagicItemTheme.values();
        for (int i = 0; i < names.length; i++) {
            if (checks[i] == null || !checks[i].isChecked()) continue;
            int rOrd = (rarities != null && i < rarities.length) ? rarities[i] : 0;
            int tOrd = (themes != null && i < themes.length) ? themes[i] : -1;
            MagicItemRarity rarity = (rOrd >= 0 && rOrd < rarityValues.length)
                    ? rarityValues[rOrd] : MagicItemRarity.COMMON;
            MagicItemTheme theme = (tOrd >= 0 && tOrd < themeValues.length)
                    ? themeValues[tOrd] : null;
            out.add(new AwardedItem(names[i], tier, rarity, theme, ts, commitId));
        }
        return out;
    }

    private static String formatItemLabel(String name, int rarityOrdinal, int themeOrdinal) {
        MagicItemRarity[] rarityValues = MagicItemRarity.values();
        MagicItemTheme[] themeValues = MagicItemTheme.values();
        String rarity = (rarityOrdinal >= 0 && rarityOrdinal < rarityValues.length)
                ? rarityLabel(rarityValues[rarityOrdinal]) : "";
        String theme = (themeOrdinal >= 0 && themeOrdinal < themeValues.length)
                ? themeLabel(themeValues[themeOrdinal]) : "Random";
        return name + "  (" + theme + " — " + rarity + ")";
    }

    private static String rarityLabel(MagicItemRarity rarity) {
        switch (rarity) {
            case COMMON: return "Common";
            case UNCOMMON: return "Uncommon";
            case RARE: return "Rare";
            case VERY_RARE: return "Very Rare";
            case LEGENDARY: return "Legendary";
            default: return "";
        }
    }

    private static String themeLabel(MagicItemTheme theme) {
        if (theme == null) return "Random";
        switch (theme) {
            case ARCANA: return "Arcana";
            case ARMAMENTS: return "Armaments";
            case IMPLEMENTS: return "Implements";
            case RELICS: return "Relics";
            default: return "Random";
        }
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) getActivity()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("Loot", text));
    }

    private int dp(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
