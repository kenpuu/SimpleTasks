package com.testmcp.simpletasks.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Task implements Serializable{
    private String descripcion;
    private String fecha;
    private String fecha_limite;
    private Estado estado;
    private Integer id;
    private ArrayList<TaskEvent> taskEvents;
    private String creator;
    private ArrayList<Integer> idsUsuariosAsignados;

    private static final String jsonDescripcion = "descripcion";
    private static final String jsonID = "id";
    private static final String jsonEstado = "estado_desc";
    private static final String jsonFecha = "fecha";
    private static final String jsonFechaLimite = "fecha_limite";
    private static final String jsonEvents = "eventos";
    private static final String jsonCreator = "created_by";
    private static final String jsonAsignedUsers = "usuarios_asignados";
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
        this.idsUsuariosAsignados = new ArrayList<>();
        if(jsonObject.has(jsonEvents)) {
            JSONArray jsonArray = jsonObject.getJSONArray(jsonEvents);
            for (int i = 0; i < jsonArray.length(); i++) {
                this.addEvent(TaskEvent.createEvent(jsonArray.getJSONObject(i)));
            }
        }
        if(jsonObject.has(jsonAsignedUsers)) {
            JSONArray jsonArray = jsonObject.getJSONArray(jsonAsignedUsers);
            for (int i = 0; i < jsonArray.length(); i++) {
                idsUsuariosAsignados.add(jsonArray.getInt(i));
            }
        }
        if(jsonObject.has(jsonCreator)) {
            this.creator = jsonObject.getString(jsonCreator);
        }
    }

    public boolean isAsigned(int userId) {
        return idsUsuariosAsignados.indexOf(userId) != -1;
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

    public Integer[] getIdsUsuariosAsignados() {
        return idsUsuariosAsignados.toArray(new Integer[idsUsuariosAsignados.size()]);
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

    public String getCreator() {
        return creator;
    }

    public void setIdsUsuariosAsignados(int[] userIDs) {
        this.idsUsuariosAsignados.clear();
        for (int i=0; i<userIDs.length; i++) idsUsuariosAsignados.add(userIDs[i]);
    }
}
