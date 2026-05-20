package com.dante.paul.dd5erandomlootgenerator.TypesOfLoot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
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
        String lootTitle = args.getString("loot_summary", "");
        final String[] itemNames = args.getStringArray("item_names");
        final int[] itemRarities = args.getIntArray("item_rarities");
        final int[] itemThemes = args.getIntArray("item_themes");
        int partyTierOrdinal = args.getInt("party_tier", -1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(lootTitle)
                .setMessage(lootMessage);

        if (itemNames != null && itemNames.length > 0 && partyTierOrdinal >= 0) {
            final CampaignStore store = new CampaignStore(getActivity());
            final Campaign active = store.getActive();
            final TierOfPlay tier = TierOfPlay.values()[partyTierOrdinal];
            builder.setNeutralButton(R.string.copy_and_commit_to_campaign, (dialog, which) -> {
                copyToClipboard(lootMessage);
                List<AwardedItem> built = buildAwardedItems(itemNames, itemRarities, itemThemes, tier);
                active.addAwardedItems(built);
                store.saveCampaign(active);
                Toast.makeText(getActivity(), R.string.commit_to_campaign_done, Toast.LENGTH_SHORT).show();
            });
        }

        builder.setNegativeButton("Copy and Dismiss", (dialog, which) -> copyToClipboard(lootMessage));
        builder.setPositiveButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());

        return builder.create();
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) getActivity()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("Loot", text));
    }

    private static List<AwardedItem> buildAwardedItems(String[] names,
                                                       int[] rarities,
                                                       int[] themes,
                                                       TierOfPlay tier) {
        List<AwardedItem> out = new ArrayList<>(names.length);
        long ts = System.currentTimeMillis();
        String commitId = UUID.randomUUID().toString();
        MagicItemRarity[] rarityValues = MagicItemRarity.values();
        MagicItemTheme[] themeValues = MagicItemTheme.values();
        for (int i = 0; i < names.length; i++) {
            int rOrdinal = (rarities != null && i < rarities.length) ? rarities[i] : 0;
            int tOrdinal = (themes != null && i < themes.length) ? themes[i] : -1;
            MagicItemRarity rarity = (rOrdinal >= 0 && rOrdinal < rarityValues.length)
                    ? rarityValues[rOrdinal] : MagicItemRarity.COMMON;
            MagicItemTheme theme = (tOrdinal >= 0 && tOrdinal < themeValues.length)
                    ? themeValues[tOrdinal] : null;
            out.add(new AwardedItem(names[i], tier, rarity, theme, ts, commitId));
        }
        return out;
    }
}
