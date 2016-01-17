package com.testmcp.simpletasks.interactor.network;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mario on 16/01/2016.
 */
public class TokenAuthPref {
    private static final String TOKEN_PREF = "simpletasks_token";
    private static final String PREF_NAME = "token";

    private static SharedPreferences tokenPrefs;

    public static void setSharedPreferences(Context context){
        tokenPrefs = context.getSharedPreferences(TOKEN_PREF, 0);
    }

    public static void save(String token){
        delete();
        SharedPreferences.Editor editor = tokenPrefs.edit();
        editor.putString(PREF_NAME, token);
        editor.commit();
    }

    public static String get() {
        return tokenPrefs.getString(PREF_NAME, null);
    }

    public static void delete() {
        SharedPreferences.Editor editor = tokenPrefs.edit();
        editor.remove(PREF_NAME);
        editor.commit();
    }
}
