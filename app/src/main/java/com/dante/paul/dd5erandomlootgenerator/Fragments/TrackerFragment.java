package com.dante.paul.dd5erandomlootgenerator.Fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.WindowManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemRarity;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.TierOfPlay;
import com.dante.paul.dd5erandomlootgenerator.R;
import com.dante.paul.dd5erandomlootgenerator.Tracker.AwardedItemsActivity;
import com.dante.paul.dd5erandomlootgenerator.Tracker.Campaign;
import com.dante.paul.dd5erandomlootgenerator.Tracker.CampaignStore;


public class TrackerFragment extends Fragment {

    private static final TierOfPlay[] TIERS = TierOfPlay.values();
    private static final MagicItemRarity[] RARITIES = MagicItemRarity.values();

    private CampaignStore store;
    private TableLayout grid;
    private TextView totalView;
    private View view;
    private android.content.SharedPreferences.OnSharedPreferenceChangeListener campaignPrefsListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tracker, container, false);
        store = new CampaignStore(getActivity());
        grid = view.findViewById(R.id.tracker_grid);
        totalView = view.findViewById(R.id.tracker_total);

        view.findViewById(R.id.tracker_new).setOnClickListener(v -> promptNewCampaign());
        view.findViewById(R.id.tracker_rename).setOnClickListener(v -> promptRenameCampaign());
        view.findViewById(R.id.tracker_reset).setOnClickListener(v -> promptResetCampaign());
        view.findViewById(R.id.tracker_delete).setOnClickListener(v -> promptDeleteCampaign());
        view.findViewById(R.id.tracker_view_history).setOnClickListener(v -> {
            Campaign active = store.getActive();
            startActivity(AwardedItemsActivity.intent(getActivity(), active.getId()));
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        rebuildGrid();
        // Watch the campaign prefs file so commits made from the loot dialog
        // refresh the grid even if the Tracker tab was already loaded (which
        // it usually is — it's adjacent to Treasure in the ViewPager, so its
        // onResume only fires once on initial creation).
        android.content.SharedPreferences prefs = getActivity()
                .getApplicationContext()
                .getSharedPreferences("LootGenCampaigns", android.content.Context.MODE_PRIVATE);
        campaignPrefsListener = (sharedPreferences, key) -> {
            if (isAdded() && view != null) {
                rebuildGrid();
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(campaignPrefsListener);
    }

    @Override
    public void onPause() {
        if (campaignPrefsListener != null) {
            getActivity()
                    .getApplicationContext()
                    .getSharedPreferences("LootGenCampaigns", android.content.Context.MODE_PRIVATE)
                    .unregisterOnSharedPreferenceChangeListener(campaignPrefsListener);
            campaignPrefsListener = null;
        }
        super.onPause();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // ViewPager v1 keeps adjacent fragments resumed, so onResume doesn't
        // fire when the user swipes back to the Tracker after committing
        // from another tab. Refresh when the tab becomes visible so the
        // grid reflects the latest counts.
        if (isVisibleToUser && isAdded() && view != null) {
            rebuildGrid();
        }
    }

    private void rebuildGrid() {
        grid.removeAllViews();
        Campaign active = store.getActive();
        grid.addView(buildHeaderRow());
        for (TierOfPlay tier : TIERS) grid.addView(buildTierRow(active, tier));
        totalView.setText(getString(R.string.tracker_total_format, active.totalCount()));
    }

    private TableRow buildHeaderRow() {
        TableRow row = new TableRow(getActivity());
        row.addView(headerCell(getString(R.string.tracker_header_tier)));
        row.addView(headerCell(getString(R.string.tracker_rarity_common)));
        row.addView(headerCell(getString(R.string.tracker_rarity_uncommon)));
        row.addView(headerCell(getString(R.string.tracker_rarity_rare)));
        row.addView(headerCell(getString(R.string.tracker_rarity_very_rare_short)));
        row.addView(headerCell(getString(R.string.tracker_rarity_legendary_short)));
        return row;
    }

    private TableRow buildTierRow(Campaign campaign, TierOfPlay tier) {
        TableRow row = new TableRow(getActivity());
        row.addView(headerCell(tierLabel(tier)));
        for (MagicItemRarity rarity : RARITIES) row.addView(buildCountCell(campaign, tier, rarity));
        return row;
    }

    private TextView headerCell(String text) {
        TextView tv = new TextView(getActivity());
        tv.setText(text);
        tv.setTextSize(12);
        tv.setTypeface(tv.getTypeface(), android.graphics.Typeface.BOLD);
        tv.setTextColor(Color.BLACK);
        tv.setPadding(dp(2), dp(6), dp(2), dp(6));
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    private TextView buildCountCell(Campaign campaign, TierOfPlay tier, MagicItemRarity rarity) {
        int count = campaign.getCount(tier, rarity);
        int target = Campaign.targetForTierAndRarity(tier, rarity);
        TextView tv = new TextView(getActivity());
        tv.setPadding(dp(2), dp(10), dp(2), dp(10));
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(14);
        if (target == 0 && count == 0) {
            tv.setText("—");
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setText(count + " / " + target);
            if (count > target) {
                tv.setTextColor(Color.parseColor("#C62828"));
            } else if (target > 0 && count == target) {
                tv.setTextColor(Color.parseColor("#2E7D32"));
            } else {
                tv.setTextColor(Color.BLACK);
            }
        }
        tv.setClickable(true);
        tv.setOnClickListener(v -> promptEditCell(campaign, tier, rarity));
        return tv;
    }

    private void promptEditCell(Campaign campaign, TierOfPlay tier, MagicItemRarity rarity) {
        NumberPicker picker = new NumberPicker(getActivity());
        picker.setMinValue(0);
        picker.setMaxValue(999);
        picker.setWrapSelectorWheel(false);
        picker.setValue(campaign.getCount(tier, rarity));
        String title = getString(R.string.tracker_edit_dialog_title_format,
                tierLabel(tier), rarityLabel(rarity));
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setView(wrapWithPadding(picker))
                .setPositiveButton(android.R.string.ok, (d, which) -> {
                    picker.clearFocus();
                    campaign.setCount(tier, rarity, picker.getValue());
                    store.saveCampaign(campaign);
                    rebuildGrid();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void promptNewCampaign() {
        EditText input = new EditText(getActivity());
        input.setHint("Campaign name");
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.tracker_new_dialog_title)
                .setView(wrapWithPadding(input))
                .setPositiveButton(android.R.string.ok, (d, which) -> {
                    String name = input.getText().toString().trim();
                    if (name.isEmpty()) return;
                    Campaign created = store.createCampaign(name);
                    store.setActive(created.getId());
                    rebuildGrid();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        showKeyboardWith(dialog, input);
    }

    private void promptRenameCampaign() {
        Campaign active = store.getActive();
        EditText input = new EditText(getActivity());
        input.setText(active.getName());
        input.setSelectAllOnFocus(true);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.tracker_rename_dialog_title)
                .setView(wrapWithPadding(input))
                .setPositiveButton(android.R.string.ok, (d, which) -> {
                    String name = input.getText().toString().trim();
                    if (name.isEmpty()) return;
                    store.renameCampaign(active.getId(), name);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        showKeyboardWith(dialog, input);
    }

    private void showKeyboardWith(AlertDialog dialog, EditText input) {
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            input.setAutoHandwritingEnabled(false);
        }
        input.requestFocus();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        dialog.show();
    }

    private void promptResetCampaign() {
        Campaign active = store.getActive();
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.tracker_reset_confirm_title)
                .setMessage(getString(R.string.tracker_reset_confirm_message, active.getName()))
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    store.resetCampaign(active.getId());
                    rebuildGrid();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void promptDeleteCampaign() {
        Campaign active = store.getActive();
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.tracker_delete_confirm_title)
                .setMessage(getString(R.string.tracker_delete_confirm_message, active.getName()))
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    store.deleteCampaign(active.getId());
                    rebuildGrid();
                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private View wrapWithPadding(View child) {
        android.widget.FrameLayout container = new android.widget.FrameLayout(getActivity());
        int pad = dp(16);
        container.setPadding(pad, dp(8), pad, 0);
        child.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        container.addView(child);
        return container;
    }

    private int dp(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private String tierLabel(TierOfPlay tier) {
        switch (tier) {
            case TIER_1: return getString(R.string.tracker_tier_1);
            case TIER_2: return getString(R.string.tracker_tier_2);
            case TIER_3: return getString(R.string.tracker_tier_3);
            case TIER_4: default: return getString(R.string.tracker_tier_4);
        }
    }

    private String rarityLabel(MagicItemRarity rarity) {
        switch (rarity) {
            case COMMON: return getString(R.string.tracker_rarity_common);
            case UNCOMMON: return getString(R.string.tracker_rarity_uncommon);
            case RARE: return getString(R.string.tracker_rarity_rare);
            case VERY_RARE: return getString(R.string.tracker_rarity_very_rare);
            case LEGENDARY: default: return getString(R.string.tracker_rarity_legendary);
        }
    }
}
