package com.testmcp.simpletasks.view.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.interactor.OnLoginOuputInteractor;
import com.testmcp.simpletasks.interactor.network.TasksAPI;

import java.util.regex.Pattern;

/**
 * Created by mario on 17/01/2016.
 */
public class UserPassDialog  extends DialogPreference {
    EditText password1;
    EditText password2;
    final int minPasswordLength = 6;

    LinearLayout text_dont_match;
    LinearLayout text_match;


    View view;
    String usernameKey = "usuario";
    public UserPassDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPersistent(false);
        setDialogLayoutResource(R.layout.user_pass_dialog);
    }

    private String getPrefUsername() {
        return PreferenceManager
                .getDefaultSharedPreferences(getContext())
                .getString(getContext().getString(R.string.pref_user_name_key), "");
    }

    private String getPassword1() {
        return password1.getText().toString();
    }

    private String getPassword2() {
        return password2.getText().toString();
    }

    private void disableOK(){
        text_dont_match.setVisibility(View.VISIBLE);
        text_match.setVisibility(View.INVISIBLE);
        ((AlertDialog)getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }

    private void enableOK() {
        text_dont_match.setVisibility(View.INVISIBLE);
        text_match.setVisibility(View.VISIBLE);
        ((AlertDialog)getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
    }

    @Override
    protected void onBindDialogView(View view) {
        password1 = (EditText) view.findViewById(R.id.EditText_Pwd1);
        password2 = (EditText) view.findViewById(R.id.EditText_Pwd2);
        text_dont_match = (LinearLayout) view.findViewById(R.id.layout_incorrect_passwords);
        text_match = (LinearLayout) view.findViewById(R.id.layout_correct_passwords);
        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if( getPassword1().equals(getPassword2()) &&
                    getPassword1().length() > minPasswordLength &&
                    getPassword2().length() > minPasswordLength) {
                    enableOK();
                } else {
                    disableOK();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };
        password2.addTextChangedListener(textWatcher);
        password1.addTextChangedListener(textWatcher);
        super.onBindDialogView(view);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        // When the user selects "OK", persist the new value
        if (positiveResult) {
            TasksAPI.login(AuthPref.getUsername(), getPassword1(), new OnLoginOuputInteractor());
        }
    }
}
