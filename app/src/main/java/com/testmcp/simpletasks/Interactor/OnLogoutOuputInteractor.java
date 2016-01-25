package com.testmcp.simpletasks.interactor;

import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.view.settings.AuthPref;

/**
 * Created by mario on 06/01/2016.
 */
public class OnLogoutOuputInteractor implements TasksAPI.OnLogoutIteractorInterface {
    public OnLogoutOuputInteractor() {

    }

    @Override
    public void postLogout() {
        AuthPref.delete();
    }
}
