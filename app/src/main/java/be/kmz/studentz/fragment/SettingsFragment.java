package be.kmz.studentz.fragment;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import be.kmz.studentz.R;

public class SettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.fragment_settings);
    }
}
