package com.dante.paul.dd5erandomlootgenerator.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.ChallengeRating;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.MagicItemTheme;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.RulesEdition;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.TierOfPlay;
import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.TypeOfEncounter;
import com.dante.paul.dd5erandomlootgenerator.LootList;
import com.dante.paul.dd5erandomlootgenerator.R;
import com.dante.paul.dd5erandomlootgenerator.Settings.SettingsManager;
import com.dante.paul.dd5erandomlootgenerator.TreasureCreationClasses.Treasure;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.GenerateLootMessage;

public class TreasureFragment extends Fragment {
    Spinner challengeSpinner, themeSpinner, iterationSpinner;
    TextView challengeLabel, themeLabel;
    RadioGroup typeOfEncounter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.treasure, container, false);

        challengeSpinner = view.findViewById(R.id.challenge_spinner);
        challengeLabel = view.findViewById(R.id.challenge_label);
        themeSpinner = view.findViewById(R.id.theme_spinner);
        themeLabel = view.findViewById(R.id.theme_label);
        iterationSpinner = view.findViewById(R.id.iteration_spinner);
        typeOfEncounter = view.findViewById(R.id.radio_encounter);

        String[] iterationArray = new String[500];
        for (int i = 0; i < 500; i++) iterationArray[i] = Integer.toString(i + 1);
        ArrayAdapter<CharSequence> iterAdapter = new ArrayAdapter<>(
                getActivity(), R.layout.spinner, iterationArray);
        iterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iterationSpinner.setAdapter(iterAdapter);

        Button button = view.findViewById(R.id.treasure_send);
        button.setOnClickListener(v -> generateTreasure());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        applyRulesEditionUi();
    }

    private void applyRulesEditionUi() {
        RulesEdition edition = SettingsManager.getRulesEdition(getActivity());
        if (edition == RulesEdition.RULES_2024) {
            challengeLabel.setText(R.string.tier);
            setSpinnerArray(challengeSpinner, R.array.tier_array);
            themeLabel.setVisibility(View.VISIBLE);
            themeSpinner.setVisibility(View.VISIBLE);
            setSpinnerArray(themeSpinner, R.array.theme_array);
            typeOfEncounter.setVisibility(View.GONE);
        } else {
            challengeLabel.setText(R.string.challenge);
            setSpinnerArray(challengeSpinner, R.array.challenge_array);
            themeLabel.setVisibility(View.GONE);
            themeSpinner.setVisibility(View.GONE);
            typeOfEncounter.setVisibility(View.VISIBLE);
        }
    }

    private void setSpinnerArray(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), arrayResId, R.layout.spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void generateTreasure() {
        LootList list = LootList.getInstance();
        list.deleteAll();
        int iterations = Integer.parseInt(iterationSpinner.getSelectedItem().toString());
        RulesEdition edition = SettingsManager.getRulesEdition(getActivity());
        String lootSummary;

        if (edition == RulesEdition.RULES_2024) {
            TierOfPlay tier = getTier(challengeSpinner.getSelectedItemPosition());
            MagicItemTheme theme = getTheme(themeSpinner.getSelectedItemPosition());
            Treasure treasure = new Treasure(tier, theme, iterations);
            treasure.generateTreasure();
            lootSummary = "2024 — " + tierLabel(tier) + ", " + themeLabel(theme) + " x" + iterations;
        } else {
            String challengeRatingString = challengeSpinner.getSelectedItem().toString();
            ChallengeRating challengeRating = getChallengeRating(challengeRatingString);
            Treasure treasure;
            if (typeOfEncounter.getCheckedRadioButtonId() == R.id.radio_individual) {
                treasure = new Treasure(challengeRating, TypeOfEncounter.INDIVIDUAL, iterations);
                treasure.generateTreasure();
                lootSummary = "Challenge Level " + challengeRatingString + "\nIndividual Treasure x" + iterations;
            } else {
                treasure = new Treasure(challengeRating, TypeOfEncounter.HORDE, iterations);
                treasure.generateTreasure();
                lootSummary = "Challenge Level " + challengeRatingString + "\nHoard Treasure x" + iterations;
            }
        }

        DialogFragment how = new GenerateLootMessage();
        Bundle args = new Bundle();
        args.putString("loot_summary", lootSummary);
        args.putString("loot", list.getTreasure());
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
        // Array order: Random, Arcana, Armaments, Implements, Relics.
        // Until Armaments/Implements/Relics tables are added, those selections
        // fall back to Arcana via MagicItem2024Registry.
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
