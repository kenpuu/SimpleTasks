package com.testmcp.simpletasks.view.settings;

import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.testmcp.simpletasks.R;

import android.preference.PreferenceActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Pattern;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    EditTextPreference editPref_user;
    private EditText editText_user;
    final Pattern pattern = Pattern.compile("^(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
    private DialogPreference dialogPreference;

    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    private void disableOK(){
        AlertDialog textDialog = ((AlertDialog) editPref_user.getDialog());
        if(textDialog != null) {
            textDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        }
    }

    private void enableOK() {
        AlertDialog textDialog = ((AlertDialog) editPref_user.getDialog());
        if(textDialog != null) {
            textDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        }
    }

    private String getUsername() {
        return editText_user.getText().toString();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_user_name_key)));
        editPref_user = (EditTextPreference) getPreferenceScreen().findPreference(getString(R.string.pref_user_name_key));
        editText_user = editPref_user.getEditText();
        dialogPreference = (DialogPreference) getPreferenceScreen().findPreference(getString(R.string.password_dialog_key));
        //if (pattern.matcher(AuthPref.getUsername()).matches()) dialogPreference.setEnabled(true);
        //disableOK();
        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if( pattern.matcher(getUsername()).matches()) {
                    enableOK();
                } else {
                    disableOK();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };
        editText_user.addTextChangedListener(textWatcher);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (dialogPreference != null) dialogPreference.setEnabled(true);
        //return true;
        EditTextPreference editTextPreference = (EditTextPreference) preference;
        AuthPref.setUsername(editTextPreference.getText());
        return true;
    }

}