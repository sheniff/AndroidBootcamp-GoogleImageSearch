package com.sheniff.googleimagesearch.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sheniff.googleimagesearch.R;
import com.sheniff.googleimagesearch.models.ImageSearchSettings;


public class SettingsFragment extends DialogFragment {

    private EditText etSiteFilter;
    private Spinner spImageSize;
    private Spinner spImageType;
    private Spinner spImageColor;
    private ImageSearchSettings settings;

    public SettingsFragment() {}

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
        spImageSize = (Spinner) view.findViewById(R.id.spImageSize);
        spImageType = (Spinner) view.findViewById(R.id.spImageType);
        spImageColor = (Spinner) view.findViewById(R.id.spImageColor);
        settings = getArguments().getParcelable("settings");

        // set dialog title
        getDialog().setTitle(R.string.settings_dialog_title);
        // set previous settings
        etSiteFilter.setText(settings.getSite());
        spImageSize.setSelection(settings.getSize());
        spImageType.setSelection(settings.getType());
        spImageColor.setSelection(settings.getColor());

        // listeners
        Button btnSave = (Button) view.findViewById(R.id.btnSettingsSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
                getDialog().dismiss();
            }
        });

        Button btnCancel = (Button) view.findViewById(R.id.btnSettingsCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    public void saveSettings() {
        settings.setSite(etSiteFilter.getText().toString());
        settings.setSize(spImageSize.getSelectedItemPosition());
        settings.setType(spImageType.getSelectedItemPosition());
        settings.setColor(spImageColor.getSelectedItemPosition());
    }
}
