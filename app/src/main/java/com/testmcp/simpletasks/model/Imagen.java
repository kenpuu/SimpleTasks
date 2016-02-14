package com.testmcp.simpletasks.model;

import android.graphics.Bitmap;

import com.testmcp.simpletasks.interactor.network.TasksAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class Imagen extends TaskEvent {
    private String imagenUrlStr;
    private Bitmap imagenBitmap;

    public void setImagenBitmap(Bitmap imagenBitmap) {
        this.imagenBitmap = imagenBitmap;
    }

    public Bitmap getImagenBitmap() {

        return imagenBitmap;
    }

    private final static String jsonImagen = "imagen";

    /*public CambioEstado(Integer id, String fecha, String usuario, String estado) {
        super(id, fecha, usuario);
        this.estado = estado;
    }*/

    public Imagen(JSONObject jsonObject) throws JSONException {
        super(jsonObject.getInt(jsonID), jsonObject.getString(jsonFecha), jsonObject.getString(jsonUsuario));
        this.imagenUrlStr = jsonObject.getString(jsonImagen);
        //TasksAPI.getImgTask(this);
        TasksAPI.getImgTask(this,true);
    }

    @Override
    public String toString() {
        return this.imagenUrlStr;
    }

    public String getImagenUrlStr() {
        return imagenUrlStr;
    }
}
