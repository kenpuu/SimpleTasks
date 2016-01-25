package com.testmcp.simpletasks.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by mario on 25/01/2016.
 */
public class SimpleAlert {
    public static AlertDialog SimpleAlert(String titulo, String mensaje, Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensaje);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        return alertDialog;
    }
}
