package com.dante.paul.dd5erandomlootgenerator.TypesOfLoot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemRarity;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.TierOfPlay;
import com.dante.paul.dd5erandomlootgenerator.R;
import com.dante.paul.dd5erandomlootgenerator.Tracker.Campaign;
import com.dante.paul.dd5erandomlootgenerator.Tracker.CampaignStore;

public class GenerateLootMessage extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String lootMessage = args.getString("loot", "");
        String lootTitle = args.getString("loot_summary", "");
        int[] rarityCounts = args.getIntArray("rarity_counts");
        int partyTierOrdinal = args.getInt("party_tier", -1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(lootTitle)
                .setMessage(lootMessage);

        if (rarityCounts != null && partyTierOrdinal >= 0 && hasAnyCounts(rarityCounts)) {
            CampaignStore store = new CampaignStore(getActivity());
            Campaign active = store.getActive();
            TierOfPlay tier = TierOfPlay.values()[partyTierOrdinal];
            String commitLabel = getString(R.string.commit_to_campaign_format, active.getName());
            builder.setNeutralButton(commitLabel, (dialog, which) -> {
                applyCountsToCampaign(store, active, tier, rarityCounts);
                Toast.makeText(getActivity(), R.string.commit_to_campaign_done, Toast.LENGTH_SHORT).show();
            });
        }

        builder.setPositiveButton("Copy and Dismiss", (dialog, which) -> {
            ClipboardManager clipboardManager = (ClipboardManager) getActivity()
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText("Loot", lootMessage));
        });

        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());

        return builder.create();
    }

    private static boolean hasAnyCounts(int[] counts) {
        for (int c : counts) if (c > 0) return true;
        return false;
    }

    private static void applyCountsToCampaign(CampaignStore store,
                                              Campaign campaign,
                                              TierOfPlay tier,
                                              int[] rarityCounts) {
        MagicItemRarity[] rarities = MagicItemRarity.values();
        for (int i = 0; i < rarityCounts.length && i < rarities.length; i++) {
            int n = rarityCounts[i];
            for (int j = 0; j < n; j++) {
                campaign.increment(tier, rarities[i]);
            }
        }
        store.saveCampaign(campaign);
    }
}
