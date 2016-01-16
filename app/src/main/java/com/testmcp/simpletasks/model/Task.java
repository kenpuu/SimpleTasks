package com.testmcp.simpletasks.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Task implements Serializable{
    String descripcion;
    String fecha;
    String fecha_limite;
    Estado estado;
    Integer id;
    ArrayList<TaskEvent> taskEvents;

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
        this.taskEvents = new ArrayList<>();
        if(jsonObject.has(jsonEvents)) {
            JSONArray jsonArray = jsonObject.getJSONArray(jsonEvents);
            for (int i = 0; i < jsonArray.length(); i++) {
                this.addEvent(TaskEvent.createEvent(jsonArray.getJSONObject(i)));
            }
        }
    }

    public void addEvent(TaskEvent ev) {
        /*if(ev instanceof Comment) {
            Comment comment = (Comment)ev;
            taskEvents.add(comment);
        }*/
        taskEvents.add(ev);
    }

    public String toString() {
        return id + " - " + descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TaskEvent getLastEvent() {
        if (taskEvents.size() > 0) {
            return TaskEvent.getEvent(taskEvents.get(taskEvents.size() - 1));
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

    public ArrayList<TaskEvent> getTaskEvents() {
        return taskEvents;
    }

    public Integer getId() {
        return id;
    }
}
