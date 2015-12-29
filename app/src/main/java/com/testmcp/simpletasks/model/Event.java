package com.testmcp.simpletasks.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by mario on 26/12/2015.
 */

public class Event implements Serializable{
    Integer id;
    String fecha;
    String usuario;

    protected static final String jsonID = "id";
    protected static final String jsonUsuario = "usuario";
    protected static final String jsonFecha = "fecha";
    private static final String jsonTipo = "tipo";

    public Event(Integer id, String fecha, String usuario){
        this.id = id;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public static Event createEvent(JSONObject jsonObject) throws JSONException {
        String tipo = jsonObject.getString(jsonTipo);
        switch (tipo) {
            case "cometario":
                return new Comment(jsonObject);
            case "cambio_estado":
                return new CambioEstado(jsonObject);
        }
        return null;
    }

    public static Event getEvent(Event event) {
        if (event instanceof Comment) {
            return (Comment) event;
        } else if (event instanceof CambioEstado) {
            return (CambioEstado) event;
        } else {
            return null;
        }
    }
}
