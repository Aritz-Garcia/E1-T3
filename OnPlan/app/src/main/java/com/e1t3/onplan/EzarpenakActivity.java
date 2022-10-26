package com.e1t3.onplan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

public class EzarpenakActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setDayNight();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private SharedPreferences sp;
        private SharedPreferences.Editor editor;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            SwitchPreferenceCompat switchPreference = (SwitchPreferenceCompat) findPreference(getString(R.string.settings_visual));
            sp = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
            editor = sp.edit();

            if (switchPreference != null) {
                switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                        boolean oscuro = (Boolean) newValue;
                        if (oscuro) {
                            editor.putBoolean("oscuro", true);
                        } else {
                            editor.putBoolean("oscuro", false);
                        }
                        editor.commit();
                        EzarpenakActivity ezarpenakActivity = new EzarpenakActivity();
                        ezarpenakActivity.setDayNight();
                        return false;
                    }
                });
            }
        }
    }

    public void setDayNight() {
        editor.putBoolean("oscuro", false);
        editor.commit();
        boolean oscuro = sharedPreferences.getBoolean("oscuro", true);
        if (oscuro) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

}