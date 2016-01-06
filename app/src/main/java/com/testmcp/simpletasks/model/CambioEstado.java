package com.testmcp.simpletasks.model;

import org.json.JSONException;
import org.json.JSONObject;

public class CambioEstado extends TaskEvent {
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

    public Estado getEstado() {
        return estado;
    }

    public String getStrEstado() {
        return estado.toString();
    }
}
