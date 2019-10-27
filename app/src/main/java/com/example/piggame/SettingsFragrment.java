package com.example.piggame;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsFragrment extends PreferenceFragment{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
