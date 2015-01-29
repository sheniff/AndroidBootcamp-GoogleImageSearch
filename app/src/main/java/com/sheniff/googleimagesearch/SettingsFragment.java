package com.sheniff.googleimagesearch;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sheniff.googleimagesearch.models.ImageSearchSettings;


public class SettingsFragment extends DialogFragment {

    private EditText etSiteFilter;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(ImageSearchSettings settings) {
        SettingsFragment frag = new SettingsFragment();
        Bundle args = new Bundle();
        args.putParcelable("settings", settings);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container);
        etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);
        ImageSearchSettings settings = getArguments().getParcelable("settings");

        // set dialog title
        getDialog().setTitle(R.string.settings_dialog_title);
        // set previous settings
        etSiteFilter.setText(settings.getSite());
        return view;
    }
}
