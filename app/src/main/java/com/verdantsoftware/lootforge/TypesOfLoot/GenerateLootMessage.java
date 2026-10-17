package com.verdantsoftware.lootforge.TypesOfLoot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemRarity;
import com.verdantsoftware.lootforge.EnumeratedClasses.MagicItemTheme;
import com.verdantsoftware.lootforge.EnumeratedClasses.TierOfPlay;
import com.verdantsoftware.lootforge.R;
import com.verdantsoftware.lootforge.Tracker.AwardedItem;
import com.verdantsoftware.lootforge.Tracker.Campaign;
import com.verdantsoftware.lootforge.Tracker.CampaignStore;
import com.verdantsoftware.lootforge.WikidotUrls;

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
        final String[] hoardCoinSummaries = args.getStringArray("hoard_coin_summaries");
        final int[] itemHoardIndex = args.getIntArray("item_hoard_index");
        int partyTierOrdinal = args.getInt("party_tier", -1);
        final boolean has2024Items =
                itemNames != null && itemNames.length > 0 && partyTierOrdinal >= 0;
        final boolean isMultiHoard =
                hoardCoinSummaries != null && hoardCoinSummaries.length > 1
                        && itemHoardIndex != null && itemHoardIndex.length == itemNames.length;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(lootTitle);

        final String prefixText = (has2024Items && !isMultiHoard) ? extractNonItemsPrefix(lootMessage) : "";
        final CheckBox[] checks = has2024Items
                ? new CheckBox[itemNames.length]
                : null;

        if (has2024Items) {
            if (isMultiHoard) {
                builder.setView(buildSectionedItemsView(
                        hoardCoinSummaries, itemHoardIndex,
                        itemNames, itemRarities, itemThemes, checks));
            } else {
                builder.setView(buildItemsView(prefixText, itemNames, itemRarities, itemThemes, checks));
            }
            final CampaignStore store = new CampaignStore(getActivity());
            final Campaign active = store.getActive();
            final TierOfPlay tier = TierOfPlay.values()[partyTierOrdinal];
            builder.setPositiveButton(R.string.copy_and_commit_to_campaign, (dialog, which) -> { /* overridden in OnShowListener */ });
            builder.setNegativeButton("Copy and Dismiss", (dialog, which) -> {
                String text = isMultiHoard
                        ? buildSectionedFilteredText(hoardCoinSummaries, itemHoardIndex, itemNames, itemRarities, itemThemes, checks)
                        : buildFilteredLootText(prefixText, itemNames, itemRarities, itemThemes, checks);
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
                    String text = isMultiHoard
                            ? buildSectionedFilteredText(hoardCoinSummaries, itemHoardIndex, itemNames, itemRarities, itemThemes, checks)
                            : buildFilteredLootText(prefixText, itemNames, itemRarities, itemThemes, checks);
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

        CheckBox header = new CheckBox(getActivity());
        header.setText("Items:");
        header.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimension(R.dimen.text_dialog_body));
        header.setTypeface(header.getTypeface(), Typeface.BOLD);
        header.setChecked(true);
        header.setPadding(0, dp(4), 0, dp(4));
        container.addView(header);

        // Indent the item rows so the "Items:" master sits slightly to the left.
        LinearLayout itemsContainer = new LinearLayout(getActivity());
        itemsContainer.setOrientation(LinearLayout.VERTICAL);
        itemsContainer.setPadding(dp(16), 0, 0, 0);
        java.util.List<CheckBox> allChecks = new java.util.ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            itemsContainer.addView(buildItemRow(i, names, rarities, themes, checks,
                    getResources().getDimension(R.dimen.text_dialog_body)));
            allChecks.add(checks[i]);
        }
        container.addView(itemsContainer);
        wireMasterCheckbox(header, allChecks);

        ScrollView scroll = new ScrollView(getActivity());
        scroll.addView(container, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return scroll;
    }

    /**
     * Build one checkbox + wikidot-link-icon row for the items list.
     * The CheckBox keeps its existing selection role; the trailing ⓘ
     * opens the item's wikidot page (or a site-search fallback) in the
     * device browser.
     */
    private LinearLayout buildItemRow(int i,
                                      String[] names,
                                      int[] rarities,
                                      int[] themes,
                                      CheckBox[] checks,
                                      float bodyTextSize) {
        LinearLayout row = new LinearLayout(getActivity());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(android.view.Gravity.CENTER_VERTICAL);

        CheckBox cb = new CheckBox(getActivity());
        cb.setText(formatItemLabel(names[i], rarities[i], themes[i]));
        cb.setChecked(true);
        cb.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, bodyTextSize);
        LinearLayout.LayoutParams cbParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        row.addView(cb, cbParams);
        checks[i] = cb;

        TextView info = new TextView(getActivity());
        info.setText("ⓘ");
        info.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, bodyTextSize * 1.2f);
        final String itemName = names[i];
        boolean direct = com.verdantsoftware.lootforge.WikidotUrls
                .hasDirectMatch(getActivity(), itemName);
        // Blue (link) when we have a known wikidot page; grey (de-emphasized)
        // when we'll fall back to wikidot site search — likely no results,
        // but still tappable in case the item gets added to the wiki.
        info.setTextColor(direct ? 0xFF1565C0 : 0xFF9E9E9E);
        info.setPadding(dp(12), dp(8), dp(4), dp(8));
        info.setOnClickListener(v -> openWikidot(itemName));
        row.addView(info);

        return row;
    }

    /**
     * Bind a "select all" master checkbox to a group of item checkboxes.
     *
     * <ul>
     *   <li>Toggling the master propagates to every child.</li>
     *   <li>Unchecking any child unchecks the master.</li>
     *   <li>When every child becomes checked again, the master re-checks.</li>
     * </ul>
     *
     * The {@code syncing} flag prevents listener recursion when the master
     * or a child is set programmatically by the other side.
     */
    private static void wireMasterCheckbox(CheckBox master, java.util.List<CheckBox> children) {
        final boolean[] syncing = {false};
        master.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (syncing[0]) return;
            syncing[0] = true;
            for (CheckBox c : children) c.setChecked(isChecked);
            syncing[0] = false;
        });
        for (CheckBox child : children) {
            child.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (syncing[0]) return;
                boolean allChecked = true;
                for (CheckBox c : children) {
                    if (!c.isChecked()) { allChecked = false; break; }
                }
                syncing[0] = true;
                master.setChecked(allChecked);
                syncing[0] = false;
            });
        }
    }

    private void openWikidot(String itemName) {
        String url = WikidotUrls.urlFor(getActivity(), itemName);
        try {
            android.content.Intent intent = new android.content.Intent(
                    android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url));
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getActivity(), R.string.no_browser_app, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Multi-hoard variant of {@link #buildItemsView}: one section per hoard,
     * each with its own coin line, items header, and checkboxes, separated
     * by a thin divider. {@code checks} is populated in name-array order
     * (so existing commit/copy logic that indexes into it still works).
     */
    private ScrollView buildSectionedItemsView(String[] hoardCoins,
                                               int[] itemHoardIndex,
                                               String[] names,
                                               int[] rarities,
                                               int[] themes,
                                               CheckBox[] checks) {
        LinearLayout container = new LinearLayout(getActivity());
        container.setOrientation(LinearLayout.VERTICAL);
        int hPad = dp(24);
        container.setPadding(hPad, dp(8), hPad, 0);

        float bodySize = getResources().getDimension(R.dimen.text_dialog_body);
        int numHoards = hoardCoins.length;

        for (int h = 0; h < numHoards; h++) {
            TextView hoardHeader = new TextView(getActivity());
            hoardHeader.setText("Hoard " + (h + 1) + " of " + numHoards + ":");
            hoardHeader.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, bodySize);
            hoardHeader.setTypeface(hoardHeader.getTypeface(), Typeface.BOLD);
            hoardHeader.setPadding(0, h == 0 ? 0 : dp(16), 0, dp(4));
            container.addView(hoardHeader);

            TextView coinsView = new TextView(getActivity());
            coinsView.setText("Coins: " + hoardCoins[h]);
            coinsView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, bodySize);
            coinsView.setPadding(0, 0, 0, dp(8));
            container.addView(coinsView);

            // Collect this hoard's item checkboxes so we can wire a
            // master "Items:" toggle that selects/deselects them as a group.
            java.util.List<CheckBox> hoardChecks = new java.util.ArrayList<>();
            boolean any = false;
            // Build rows first so the master checkbox below can reference them.
            // Indent items so the "Items:" master sits slightly to the left.
            android.widget.LinearLayout itemsContainer = new android.widget.LinearLayout(getActivity());
            itemsContainer.setOrientation(android.widget.LinearLayout.VERTICAL);
            itemsContainer.setPadding(dp(16), 0, 0, 0);
            for (int i = 0; i < names.length; i++) {
                if (itemHoardIndex[i] != h) continue;
                itemsContainer.addView(buildItemRow(i, names, rarities, themes, checks, bodySize));
                hoardChecks.add(checks[i]);
                any = true;
            }

            if (any) {
                CheckBox itemsHeader = new CheckBox(getActivity());
                itemsHeader.setText("Items:");
                itemsHeader.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, bodySize);
                itemsHeader.setTypeface(itemsHeader.getTypeface(), Typeface.BOLD);
                itemsHeader.setChecked(true);
                itemsHeader.setPadding(0, dp(4), 0, dp(4));
                wireMasterCheckbox(itemsHeader, hoardChecks);
                container.addView(itemsHeader);
                container.addView(itemsContainer);
            } else {
                TextView none = new TextView(getActivity());
                none.setText("(no magic items)");
                none.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, bodySize);
                container.addView(none);
            }

            if (h < numHoards - 1) {
                View divider = new View(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, dp(1));
                params.setMargins(0, dp(16), 0, 0);
                divider.setLayoutParams(params);
                divider.setBackgroundColor(0xFF888888);
                container.addView(divider);
            }
        }

        ScrollView scroll = new ScrollView(getActivity());
        scroll.addView(container, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return scroll;
    }

    /**
     * Multi-hoard variant of {@link #buildFilteredLootText}: emits one
     * "Hoard N of M / Coins / Items" block per hoard, divided by a
     * horizontal-rule line, with only the checked items per hoard included.
     */
    private static String buildSectionedFilteredText(String[] hoardCoins,
                                                     int[] itemHoardIndex,
                                                     String[] names,
                                                     int[] rarities,
                                                     int[] themes,
                                                     CheckBox[] checks) {
        StringBuilder sb = new StringBuilder();
        int numHoards = hoardCoins.length;
        for (int h = 0; h < numHoards; h++) {
            if (h > 0) sb.append("\r\n────────────────────────────\r\n");
            sb.append("Hoard ").append(h + 1).append(" of ").append(numHoards).append(":\r\n");
            sb.append("Coins: ").append(hoardCoins[h]).append("\r\n");
            boolean any = false;
            for (int i = 0; i < names.length; i++) {
                if (itemHoardIndex[i] != h) continue;
                if (checks[i] == null || !checks[i].isChecked()) continue;
                if (!any) {
                    sb.append("Items:\r\n");
                    any = true;
                }
                sb.append(formatItemLabel(names[i], rarities[i], themes[i])).append("\r\n");
            }
        }
        return sb.toString();
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
