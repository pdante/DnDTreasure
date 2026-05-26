package com.verdantsoftware.lootforge.Settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.verdantsoftware.lootforge.EnumeratedClasses.RulesEdition;

public final class SettingsManager {

    private static final String PREFS = "LootGenPref";
    private static final String KEY_RULES_EDITION = "rules_edition";

    private SettingsManager() {}

    public static RulesEdition getRulesEdition(Context context) {
        SharedPreferences prefs = prefs(context);
        String value = prefs.getString(KEY_RULES_EDITION, RulesEdition.RULES_2024.name());
        try {
            return RulesEdition.valueOf(value);
        } catch (IllegalArgumentException e) {
            return RulesEdition.RULES_2024;
        }
    }

    public static void setRulesEdition(Context context, RulesEdition edition) {
        prefs(context).edit().putString(KEY_RULES_EDITION, edition.name()).apply();
    }

    private static SharedPreferences prefs(Context context) {
        return context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
}
