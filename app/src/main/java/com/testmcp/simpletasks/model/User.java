package com.testmcp.simpletasks.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mario on 25/01/2016.
 */
public class User {
    private int id;
    private String username;

    private static final String jsonUsername = "username";
    private static final String jsonID = "id";

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(JSONObject jsonObject) throws JSONException {
        this.username = jsonObject.getString(jsonUsername);
        this.id = jsonObject.getInt(jsonID);

    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
