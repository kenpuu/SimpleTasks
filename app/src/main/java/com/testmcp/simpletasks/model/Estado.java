package com.testmcp.simpletasks.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by mario on 28/12/2015.
 */
public class Estado implements Serializable {
    String descripcion;
    Integer id;

    private final String jsonDescripcion = "descripcion";
    private final String jsonID = "id";

    public Estado(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt(jsonID);
        this.descripcion = jsonObject.getString(jsonDescripcion);
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
