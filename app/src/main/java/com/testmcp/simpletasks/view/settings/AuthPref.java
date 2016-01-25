package com.testmcp.simpletasks.view.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.testmcp.simpletasks.R;

/**
 * Created by mario on 16/01/2016.
 */
public class AuthPref {
    private static final String TOKEN_SHARED_PREF = "simpletasks_token";
    private static final String TOKEN_PREF_NAME = "token";
    private static String USER_PREF_NAME;
    private static SharedPreferences tokenPrefs;

    public static void setSharedPreferences(Context context){
        //tokenPrefs = context.getSharedPreferences(TOKEN_SHARED_PREF, 0);
        tokenPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        USER_PREF_NAME = context.getString(R.string.pref_user_name_key);
    }

    public static void saveToken(String token){
        delete();
        SharedPreferences.Editor editor = tokenPrefs.edit();
        editor.putString(TOKEN_PREF_NAME, token);
        editor.commit();
    }

    public static String getUsername() {
        return tokenPrefs.getString(USER_PREF_NAME, null);
    }

    public static void setUsername(String username) {
        SharedPreferences.Editor editor = tokenPrefs.edit();
        editor.putString(USER_PREF_NAME, username);
        editor.commit();
    }

    public static String getToken() {
        return tokenPrefs.getString(TOKEN_PREF_NAME, null);
    }

    public static void delete() {
        SharedPreferences.Editor editor = tokenPrefs.edit();
        editor.remove(TOKEN_PREF_NAME);
        editor.remove(USER_PREF_NAME);
        editor.commit();
    }
}
