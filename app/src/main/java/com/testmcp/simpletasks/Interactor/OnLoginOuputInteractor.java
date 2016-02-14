package com.testmcp.simpletasks.interactor;

import android.app.AlertDialog;
import android.content.Context;

import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.view.settings.AuthPref;

/**
 * Created by mario on 06/01/2016.
 */
public class OnLoginOuputInteractor implements TasksAPI.OnLoginIteractorInterface {
    Context context;
    public OnLoginOuputInteractor(Context context) {

        this.context = context;
    }
    // TODO Mostrar algo cuando haya un inicio de sesión válido
    @Override
    public void postLogin(String token) {
        AuthPref.saveToken(token);
    }

    @Override
    public void notAllowedHere() {
        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("Usuario y/o contraseña incorrectos")
                .show();
    }
}
