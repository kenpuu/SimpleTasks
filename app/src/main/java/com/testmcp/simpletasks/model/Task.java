package com.testmcp.simpletasks.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Task implements Serializable{
    String descripcion;
    String fecha;
    String fecha_limite;
    Estado estado;
    Integer id;
    ArrayList<Event> events;

    private static final String jsonDescripcion = "descripcion";
    private static final String jsonID = "id";
    private static final String jsonEstado = "estado_desc";
    private static final String jsonFecha = "fecha";
    private static final String jsonFechaLimite = "fecha_limite";
    private static final String jsonEvents = "eventos";

    public Task(String descripcion, int id) {
        this.descripcion = descripcion;
        this.id = id;
    }

    public Task(JSONObject jsonObject) throws JSONException {
        this.descripcion = jsonObject.getString(jsonDescripcion);
        this.fecha = jsonObject.getString(jsonFecha);
        this.fecha_limite =
                jsonObject.isNull(jsonFechaLimite) ?
                        null :
                        jsonObject.getString(jsonFechaLimite);
        //this.descripcion = jsonObject.getString(jsonDescripcion);
        this.id = jsonObject.getInt(jsonID);
        this.estado = new Estado(jsonObject.getJSONObject(jsonEstado));
        this.events = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray(jsonEvents);
        for (int i = 0; i< jsonArray.length(); i++){
            this.addEvent(Event.createEvent(jsonArray.getJSONObject(i)));
        }
    }

    public void addEvent(Event ev) {
        if(ev instanceof Comment) {
            Comment comment = (Comment)ev;
            events.add(comment);
        }
    }

    public String toString() {
        return id + " - " + descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Event getLastEvent() {
        if (events.size() > 0) {
            return Event.getEvent(events.get(events.size() - 1));
        } else {
            return null;
        }
    }

    public String getFecha() {
        return fecha;
    }

    public String getFecha_limite() {
        return fecha_limite;
    }

    public Estado getEstado() {
        return estado;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
