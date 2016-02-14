package com.testmcp.simpletasks.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by mario on 26/12/2015.
 */

public class TaskEvent implements Serializable{
    Integer id;
    String fecha;
    String usuario;

    protected static final String jsonID = "id";
    protected static final String jsonUsuario = "usuario";
    protected static final String jsonFecha = "fecha";
    private static final String jsonTipo = "tipo";

    public TaskEvent(Integer id, String fecha, String usuario){
        this.id = id;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public static TaskEvent createEvent(JSONObject jsonObject) throws JSONException {
        String tipo = jsonObject.getString(jsonTipo);
        switch (tipo) {
            case "comentario":
                return new Comment(jsonObject);
            case "cambio_estado":
                return new CambioEstado(jsonObject);
            case "imagen":
                return new Imagen(jsonObject);
        }
        return null;
    }

    public static TaskEvent getEvent(TaskEvent taskEvent) {
        return taskEvent;
    }

    public String getFecha() {
        return fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public Integer getId() {
        return id;
    }
}
