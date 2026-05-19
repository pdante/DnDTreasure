package com.dante.paul.dd5erandomlootgenerator.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.ChallengeRating;
import com.dante.paul.dd5erandomlootgenerator.LootList;
import com.dante.paul.dd5erandomlootgenerator.R;
import com.dante.paul.dd5erandomlootgenerator.TreasureCreationClasses.GenerateItem;
import com.dante.paul.dd5erandomlootgenerator.TypesOfLoot.GenerateLootMessage;

/**
 * Created by PaulD on 2015-12-10.
 */
public class ItemsFragment extends Fragment {
    Spinner challengeSpinner, iterationSpinner;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.items, container, false);

        //setup the CHALLENGE LEVEL SPINNER--------------------------------------------------------
        challengeSpinner = (Spinner) view.findViewById(R.id.challenge_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.challenge_array, R.layout.spinner); // Create an ArrayAdapter using the string array and a default spinner layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        challengeSpinner.setAdapter(adapter);

        //setup the ITERATION SPINNER--------------------------------------------------------------
        String[] iterationArray = new String[500];
        for (int i = 0; i < 500; i++) {
            iterationArray[i] = Integer.toString(i + 1);
        }
        iterationSpinner = (Spinner) view.findViewById(R.id.iteration_spinner);
        adapter = new ArrayAdapter<CharSequence>(getActivity(), R.layout.spinner, iterationArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        iterationSpinner.setAdapter(adapter);
        Button button = (Button) view.findViewById(R.id.item_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateItem();
            }
        });


        return view;
    }
    public void generateItem() {
        String lootSummary;
        LootList list = LootList.getInstance();
        list.deleteAll();
        challengeSpinner = (Spinner) view.findViewById(R.id.challenge_spinner);
        String challengeRatingString = challengeSpinner.getSelectedItem().toString();
        ChallengeRating challengeRating = getChallengeRating(challengeRatingString);

        int iterations = Integer.parseInt(iterationSpinner.getSelectedItem().toString());
        GenerateItem treasure = new GenerateItem(challengeRating,iterations);
        treasure.generateTreasure();
        lootSummary = "Challenge Level " + challengeRatingString + "\nIndividual Item ";
        lootSummary += " x" + iterations;

        DialogFragment how = new GenerateLootMessage();
        Bundle args = new Bundle();

        args.putString("loot_summary", lootSummary);
        args.putString("loot", list.getItems());
        how.setArguments(args);
        how.show(getActivity().getFragmentManager(), "tag");


    }
    private ChallengeRating getChallengeRating(String challengeRatingString) {
        ChallengeRating challengeRating;
        switch (challengeRatingString) {
            case "0-4":
                challengeRating = ChallengeRating.ZERO;
                break;
            case "5-10":
                challengeRating = ChallengeRating.FIVE;
                break;
            case "11-16":
                challengeRating = ChallengeRating.ELEVEN;
                break;
            default:
                challengeRating = ChallengeRating.SEVENTEEN;
                break;
        }
        return challengeRating;
    }
}
