package com.testmcp.simpletasks.model;

import com.testmcp.simpletasks.model.Estado;
import com.testmcp.simpletasks.model.Event;

import org.json.JSONException;
import org.json.JSONObject;

public class CambioEstado extends Event {
    Estado estado;

    private final static String jsonEstado = "estado_desc";

    /*public CambioEstado(Integer id, String fecha, String usuario, String estado) {
        super(id, fecha, usuario);
        this.estado = estado;
    }*/

    public CambioEstado(JSONObject jsonObject) throws JSONException {
        super(jsonObject.getInt(jsonID), jsonObject.getString(jsonFecha), jsonObject.getString(jsonUsuario));
        this.estado = new Estado(jsonObject.getJSONObject(jsonEstado));
    }

    @Override
    public String toString() {
        return fecha + " - " + usuario + ": " + estado.toString();
    }
}
