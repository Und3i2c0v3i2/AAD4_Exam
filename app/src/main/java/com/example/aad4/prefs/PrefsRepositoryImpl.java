package com.example.aad4.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;


public class PrefsRepositoryImpl implements PrefsRepository {

    private SharedPreferences sharedPreferences;


    public PrefsRepositoryImpl(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
   }


    @Override
    public boolean isAllowed(String key) {
        return sharedPreferences.getBoolean(key, false);
    }


}
