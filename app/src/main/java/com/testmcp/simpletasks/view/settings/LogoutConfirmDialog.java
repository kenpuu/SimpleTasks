package com.testmcp.simpletasks.view.settings;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import com.testmcp.simpletasks.interactor.OnLoginOuputInteractor;
import com.testmcp.simpletasks.interactor.OnLogoutOuputInteractor;
import com.testmcp.simpletasks.interactor.network.TasksAPI;

/**
 * Created by mario on 17/01/2016.
 */
public class LogoutConfirmDialog extends DialogPreference {
    public LogoutConfirmDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        // When the user selects "OK", persist the new value
        if (positiveResult) {
            TasksAPI.logout(new OnLogoutOuputInteractor());
        }
    }
}
