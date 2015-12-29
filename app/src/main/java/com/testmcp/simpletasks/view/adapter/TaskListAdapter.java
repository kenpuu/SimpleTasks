package com.testmcp.simpletasks.view.adapter;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.testmcp.simpletasks.model.Task;

public class TaskListAdapter extends BaseAdapter {

    /**
     * Aquí guardaremos todos los rectángulos que queremos
     * representar en nuestro ListView. Es recomendable usar sistemas
     * con acceso directo por posición, como el ArrayList, un Vector, un Array...
     */
    private ArrayList<Task> tasks;

    /**
     * El constructor
     * @param tasks
     */
    public TaskListAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;

        //Cada vez que cambiamos los elementos debemos noficarlo
        notifyDataSetChanged();
    }



    /**
     * Este método simplemente nos devuelve el número de
     * elementos de nuestro ListView. Evidentemente es el tamaño del arraylist
     */
    public int getCount() {
        return tasks.size();
    }

    /**
     * Este método nos devuele el elemento de una posición determinada.
     * El elemento es el Rectángulo, así que...
     */
    public Task getItem(int position) {
        return tasks.get(position);
    }

    /**
     * Aquí tenemos que devolver el ID del elemento. Del ELEMENTO, no del View.
     * Por lo general esto no se usa, así que return 0 y listo.
     */
    public long getItemId(int position) {
        return 0;
    }

    /**
     * El método más complicado. Aquí tenemos que devolver el View a representar.
     * En este método nos pasan 3 valores. El primero es la posición del elemento,
     * el segundo es el View a utilizar que será uno que ya no esté visible y que
     * lo reutilizaremos, y el último es el ViewGroup, es en nuestro caso, el ListView.
     */

    public View getView(int position, View convertView, ViewGroup parent) {
        /**
         * Lo primero que haremos es comprobar si el View ya existe y tenemos que reciclarlo
         * o por el contrario debemos crear uno nuevo.
         */

        TaskItemLayout view;
        if (convertView == null) //NO existe, creamos uno
            view = new TaskItemLayout(parent.getContext());
        else                    //Existe, reutilizamos
            view = (TaskItemLayout) convertView;

        /**
         * Ahora tenemos que darle los valores correctos, para ello usamos
         * el método setTaskItemLayout pasándole el rectángulo a mostrar
         * y finalmente devolvemos el view.
         */

        view.setTaskItemLayout(tasks.get(position));

        return view;
    }
}