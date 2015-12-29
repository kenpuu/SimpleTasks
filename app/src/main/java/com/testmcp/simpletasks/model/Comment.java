package com.testmcp.simpletasks.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Comment extends Event {
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
}
