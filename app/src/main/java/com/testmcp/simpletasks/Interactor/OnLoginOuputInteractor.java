package com.testmcp.simpletasks.interactor;

import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.view.settings.AuthPref;

/**
 * Created by mario on 06/01/2016.
 */
public class OnLoginOuputInteractor implements TasksAPI.OnLoginIteractorInterface {
    public OnLoginOuputInteractor() {

    }
    // TODO Mostrar algo cuando haya un inicio de sesión válido
    @Override
    public void postLogin(String token) {
        AuthPref.saveToken(token);
    }

    @Override
    public void notAllowedHere() {

    }
}
