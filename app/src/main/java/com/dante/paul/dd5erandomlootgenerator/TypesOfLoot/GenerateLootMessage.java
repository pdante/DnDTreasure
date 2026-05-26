package com.dante.paul.dd5erandomlootgenerator.TypesOfLoot;

import android.app.AlertDialog;
import android.app.Dialog;
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

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

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
        final FragmentActivity activity = requireActivity();
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
            builder.setPositiveButton(R.string.copy_and_commit_to_campaign, (dialog, which) -> { /* overridden in OnShowListener */ });
            builder.setNegativeButton("Copy and Dismiss", (dialog, which) -> {
                String text = buildFilteredLootText(prefixText, itemNames, itemRarities, itemThemes, checks);
                copyToClipboard(activity, text);
            });
            builder.setNeutralButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());

            final AlertDialog dialog = builder.create();
            final boolean[] alreadyWarned = {false};
            dialog.setOnShowListener(d -> {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    List<AwardedItem> built = buildCheckedAwardedItems(
                            itemNames, itemRarities, itemThemes, checks, tier);
                    List<MagicItemRarity> overMax = computeOverMaxRarities(active, tier, built);
                    boolean needWarn = !overMax.isEmpty()
                            && !active.isSuppressOverMaxWarning()
                            && !alreadyWarned[0];
                    if (needWarn) {
                        alreadyWarned[0] = true;
                        showOverMaxWarning(activity, active, store, tier, overMax);
                        return;
                    }
                    String text = buildFilteredLootText(prefixText, itemNames, itemRarities, itemThemes, checks);
                    copyToClipboard(activity, text);
                    if (!built.isEmpty()) {
                        active.addAwardedItems(built);
                        store.saveCampaign(active);
                    }
                    Toast.makeText(activity, R.string.commit_to_campaign_done, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
            });
            return dialog;
        } else {
            builder.setMessage(lootMessage);
            builder.setNegativeButton("Copy and Dismiss", (dialog, which) -> copyToClipboard(activity, lootMessage));
            builder.setNeutralButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());
            return builder.create();
        }
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
            prefixView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimension(R.dimen.text_dialog_body));
            prefixView.setGravity(Gravity.CENTER_HORIZONTAL);
            prefixView.setPadding(0, 0, 0, dp(8));
            container.addView(prefixView);
        }

        TextView header = new TextView(getActivity());
        header.setText("Items:");
        header.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimension(R.dimen.text_dialog_body));
        header.setTypeface(header.getTypeface(), Typeface.BOLD);
        header.setPadding(0, dp(4), 0, dp(4));
        container.addView(header);

        for (int i = 0; i < names.length; i++) {
            CheckBox cb = new CheckBox(getActivity());
            cb.setText(formatItemLabel(names[i], rarities[i], themes[i]));
            cb.setChecked(true);
            cb.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimension(R.dimen.text_dialog_body));
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

    private static List<MagicItemRarity> computeOverMaxRarities(Campaign campaign,
                                                                TierOfPlay tier,
                                                                List<AwardedItem> toAdd) {
        int[] adding = new int[MagicItemRarity.values().length];
        for (AwardedItem item : toAdd) {
            adding[item.rarity.ordinal()]++;
        }
        List<MagicItemRarity> over = new ArrayList<>();
        for (MagicItemRarity rarity : MagicItemRarity.values()) {
            int add = adding[rarity.ordinal()];
            if (add == 0) continue;
            int target = Campaign.targetForTierAndRarity(tier, rarity);
            int current = campaign.getCount(tier, rarity);
            if (current + add > target) {
                over.add(rarity);
            }
        }
        return over;
    }

    private void showOverMaxWarning(FragmentActivity activity,
                                    Campaign campaign,
                                    CampaignStore store,
                                    TierOfPlay tier,
                                    List<MagicItemRarity> overMax) {
        StringBuilder msg = new StringBuilder();
        msg.append("Committing these items will push the following past the DMG's recommended count for ")
                .append(tierLabel(tier)).append(":\n\n");
        for (MagicItemRarity rarity : overMax) {
            int target = Campaign.targetForTierAndRarity(tier, rarity);
            int current = campaign.getCount(tier, rarity);
            msg.append("• ").append(rarityLabel(rarity))
                    .append(": already ").append(current).append(" of ").append(target)
                    .append("\n");
        }
        msg.append("\nOver-max counts will be shown in red on the Tracker.");

        LinearLayout container = new LinearLayout(activity);
        container.setOrientation(LinearLayout.VERTICAL);
        int hPad = dp(24);
        container.setPadding(hPad, dp(16), hPad, 0);

        TextView body = new TextView(activity);
        body.setText(msg.toString());
        body.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimension(R.dimen.text_dialog_body));
        container.addView(body);

        CheckBox suppress = new CheckBox(activity);
        suppress.setText("Don't show this warning again for this campaign");
        suppress.setPadding(0, dp(16), 0, 0);
        container.addView(suppress);

        new AlertDialog.Builder(activity)
                .setTitle("Over recommended count")
                .setView(container)
                .setPositiveButton("Close", (d, w) -> {
                    if (suppress.isChecked()) {
                        campaign.setSuppressOverMaxWarning(true);
                        store.saveCampaign(campaign);
                    }
                })
                .setCancelable(false)
                .show();
    }

    private static String tierLabel(TierOfPlay tier) {
        switch (tier) {
            case TIER_1: return "Tier 1 (levels 1–4)";
            case TIER_2: return "Tier 2 (levels 5–10)";
            case TIER_3: return "Tier 3 (levels 11–16)";
            case TIER_4: return "Tier 4 (levels 17–20)";
            default: return tier.name();
        }
    }

    private static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboardManager = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("Loot", text));
    }

    private int dp(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
