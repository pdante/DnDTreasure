package com.dante.paul.dd5erandomlootgenerator.Fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.ChallengeRating;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemTheme;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.RulesEdition;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.TierOfPlay;
import com.dante.paul.dd5erandomlootgenerator.LootList;
import com.dante.paul.dd5erandomlootgenerator.R;
import com.dante.paul.dd5erandomlootgenerator.Settings.SettingsManager;
import com.dante.paul.dd5erandomlootgenerator.TreasureCreationClasses.GenerateItem;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.GenerateLootMessage;

public class ItemsFragment extends Fragment {
    Spinner challengeSpinner, partyLevelSpinner, themeSpinner, iterationSpinner;
    TextView partyLevelLabel, themeLabel;
    View view;
    private SharedPreferences.OnSharedPreferenceChangeListener prefsListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.items, container, false);

        challengeSpinner = view.findViewById(R.id.challenge_spinner);
        partyLevelSpinner = view.findViewById(R.id.party_level_spinner);
        partyLevelLabel = view.findViewById(R.id.party_level_label);
        themeSpinner = view.findViewById(R.id.theme_spinner);
        themeLabel = view.findViewById(R.id.theme_label);
        iterationSpinner = view.findViewById(R.id.iteration_spinner);

        setSpinnerArray(challengeSpinner, R.array.challenge_array);

        String[] iterationArray = new String[500];
        for (int i = 0; i < 500; i++) iterationArray[i] = Integer.toString(i + 1);
        ArrayAdapter<CharSequence> iterAdapter = new ArrayAdapter<>(
                getActivity(), R.layout.spinner, iterationArray);
        iterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iterationSpinner.setAdapter(iterAdapter);

        if (partyLevelSpinner != null) setSpinnerArray(partyLevelSpinner, R.array.party_level_array);
        if (themeSpinner != null) setSpinnerArray(themeSpinner, R.array.theme_array);

        Button button = view.findViewById(R.id.item_send);
        button.setOnClickListener(v -> generateItem());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        applyRulesEditionUi();
        SharedPreferences prefs = getActivity()
                .getApplicationContext()
                .getSharedPreferences("LootGenPref", Context.MODE_PRIVATE);
        prefsListener = (sharedPreferences, key) -> {
            if ("rules_edition".equals(key) && isAdded()) {
                applyRulesEditionUi();
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(prefsListener);
    }

    @Override
    public void onPause() {
        if (prefsListener != null) {
            getActivity()
                    .getApplicationContext()
                    .getSharedPreferences("LootGenPref", Context.MODE_PRIVATE)
                    .unregisterOnSharedPreferenceChangeListener(prefsListener);
            prefsListener = null;
        }
        super.onPause();
    }

    private void applyRulesEditionUi() {
        RulesEdition edition = SettingsManager.getRulesEdition(getActivity());
        boolean is2024 = edition == RulesEdition.RULES_2024;
        int magicVisibility = is2024 ? View.VISIBLE : View.GONE;
        int challengeVisibility = is2024 ? View.GONE : View.VISIBLE;
        // In 2024 mode, CR isn't an input for a single magic item — rarity comes
        // from Party Level and theme from the spinner. Hide the CR spinner.
        if (challengeSpinner != null) challengeSpinner.setVisibility(challengeVisibility);
        View challengeLabel = view.findViewById(R.id.challenge_label);
        if (challengeLabel != null) challengeLabel.setVisibility(challengeVisibility);
        if (partyLevelLabel != null) partyLevelLabel.setVisibility(magicVisibility);
        if (partyLevelSpinner != null) partyLevelSpinner.setVisibility(magicVisibility);
        if (themeLabel != null) themeLabel.setVisibility(magicVisibility);
        if (themeSpinner != null) themeSpinner.setVisibility(magicVisibility);
    }

    private void setSpinnerArray(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), arrayResId, R.layout.spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void generateItem() {
        LootList list = LootList.getInstance();
        list.deleteAll();
        int iterations = Integer.parseInt(iterationSpinner.getSelectedItem().toString());
        RulesEdition edition = SettingsManager.getRulesEdition(getActivity());
        String lootSummary;
        GenerateItem generator;

        if (edition == RulesEdition.RULES_2024) {
            TierOfPlay tier = getTier(partyLevelSpinner.getSelectedItemPosition());
            MagicItemTheme theme = getTheme(themeSpinner.getSelectedItemPosition());
            generator = new GenerateItem(tier, theme, iterations);
            lootSummary = "2024 Rules — " + tierLabel(tier) + ", " + themeLabel(theme)
                    + ", " + iterations + " item" + (iterations == 1 ? "" : "s");
        } else {
            String challengeRatingString = challengeSpinner.getSelectedItem().toString();
            ChallengeRating challengeRating = getChallengeRating(challengeRatingString);
            generator = new GenerateItem(challengeRating, iterations);
            lootSummary = "Challenge Level " + challengeRatingString + "\nIndividual Item x" + iterations;
        }
        generator.generateTreasure();

        DialogFragment how = new GenerateLootMessage();
        Bundle args = new Bundle();
        args.putString("loot_summary", lootSummary);
        args.putString("loot", list.getItems());
        if (edition == RulesEdition.RULES_2024) {
            args.putIntArray("rarity_counts", generator.getGeneratedRarityCounts());
            args.putInt("party_tier", generator.getPartyTier().ordinal());
        }
        how.setArguments(args);
        how.show(getActivity().getFragmentManager(), "tag");
    }

    private ChallengeRating getChallengeRating(String challengeRatingString) {
        switch (challengeRatingString) {
            case "0-4": return ChallengeRating.ZERO;
            case "5-10": return ChallengeRating.FIVE;
            case "11-16": return ChallengeRating.ELEVEN;
            default: return ChallengeRating.SEVENTEEN;
        }
    }

    private TierOfPlay getTier(int position) {
        switch (position) {
            case 0: return TierOfPlay.TIER_1;
            case 1: return TierOfPlay.TIER_2;
            case 2: return TierOfPlay.TIER_3;
            default: return TierOfPlay.TIER_4;
        }
    }

    private MagicItemTheme getTheme(int position) {
        switch (position) {
            case 1: return MagicItemTheme.ARCANA;
            case 2: return MagicItemTheme.ARMAMENTS;
            case 3: return MagicItemTheme.IMPLEMENTS;
            case 4: return MagicItemTheme.RELICS;
            default: return MagicItemTheme.RANDOM;
        }
    }

    private String tierLabel(TierOfPlay tier) {
        switch (tier) {
            case TIER_1: return "Tier 1";
            case TIER_2: return "Tier 2";
            case TIER_3: return "Tier 3";
            case TIER_4: return "Tier 4";
            default: return "Tier ?";
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
}
