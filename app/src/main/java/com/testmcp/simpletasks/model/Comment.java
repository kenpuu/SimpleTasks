package com.testmcp.simpletasks.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Comment extends TaskEvent {
    String contenido;
    protected static final String jsonContenido = "contenido";

    public Comment(JSONObject jsonObject) throws JSONException {
        super(jsonObject.getInt(jsonID), jsonObject.getString(jsonFecha), jsonObject.getString(jsonUsuario));
        this.contenido = jsonObject.getString(jsonContenido);
    }

    @Override
    public String toString() {
        return fecha + " - " + usuario + ": " + contenido;
    }

    public String getContenido() {
        return contenido;
    }
}
