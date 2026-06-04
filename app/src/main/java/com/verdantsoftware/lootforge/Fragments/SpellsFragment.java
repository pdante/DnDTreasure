package com.verdantsoftware.lootforge.Fragments;

import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.verdantsoftware.lootforge.R;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateSpell;
import com.verdantsoftware.lootforge.TreasureCreationClasses.GenerateSpellStrings;
import com.verdantsoftware.lootforge.TypesOfLoot.GenerateLootMessage;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.AbstractSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.ArtificerSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.BardSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.ClericSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.DruidSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.PaladinSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.RangerSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.SorcererSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.WarlockSpells;
import com.verdantsoftware.lootforge.TypesOfLoot.SpellTables.WizardSpells;


public class SpellsFragment extends Fragment{
    Spinner levelSpinner, classSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spells, container, false);

        //setup the CHALLENGE LEVEL SPINNER--------------------------------------------------------
        levelSpinner = (Spinner) view.findViewById(R.id.level_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.level_array, R.layout.spinner); // Create an ArrayAdapter using the string array and a default spinner layout
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        levelSpinner.setAdapter(adapter);


        //setup the CHALLENGE LEVEL SPINNER--------------------------------------------------------
        classSpinner = (Spinner) view.findViewById(R.id.class_spinner);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.class_array, R.layout.spinner); // Create an ArrayAdapter using the string array and a default spinner layout
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        classSpinner.setAdapter(adapter);
        Button button = (Button) view.findViewById(R.id.spell_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateSpell();
            }
        });


        return view;
    }
    public void generateSpell() {
        int level = -1;
        AbstractSpells casterType;
        GenerateSpell generateSpell;
        String levelString = levelSpinner.getSelectedItem().toString();
        if (!levelString.equals("Random"))
            level = Integer.parseInt(levelString);
        String casterTypeString = classSpinner.getSelectedItem().toString();
        if (!casterTypeString.equals("Random")) {
            casterType = getCasterType(casterTypeString);
            if (level == -1)
                generateSpell = new GenerateSpell(casterType, casterTypeString);
            else
                generateSpell = new GenerateSpell(casterType, casterTypeString, level);
        }else {
            if (level == -1)
                generateSpell = new GenerateSpell();
            else
                generateSpell = new GenerateSpell(level);
        }
        String lootSummary ="Generated Spell";
        GenerateSpellStrings generatedSpellStrings = generateSpell.generateSpell();
        String loot = generatedSpellStrings.getSpellCLass() + " " + generatedSpellStrings.getLevel() + "\r\n  " + generatedSpellStrings.getName() + "\r\n" + generatedSpellStrings.getMagicItemtable() + "\r\n";
        DialogFragment how = new GenerateLootMessage();
        Bundle args = new Bundle();

        args.putString("loot_summary", lootSummary);
        args.putString("loot", loot);
        how.setArguments(args);
        how.show(requireActivity().getSupportFragmentManager(), "tag");
    }
    private AbstractSpells getCasterType(String casterTypeString) {
        switch (casterTypeString) {
            case "Artificer":
                return new ArtificerSpells();
            case "Bard":
                return new BardSpells();
            case "Cleric":
                return new ClericSpells();
            case "Druid":
                return new DruidSpells();
            case "Paladin":
                return new PaladinSpells();
            case "Ranger":
                return new RangerSpells();
            case "Sorcerer":
                return new SorcererSpells();
            case "Warlock":
                return new WarlockSpells();
            default:
                return new WizardSpells();
        }
    }
}