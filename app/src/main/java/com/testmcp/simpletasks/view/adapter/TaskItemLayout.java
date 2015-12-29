package com.testmcp.simpletasks.view.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.model.Event;
import com.testmcp.simpletasks.model.Task;

public class TaskItemLayout extends LinearLayout{

    private TextView descripcion;
    private TextView fecha;

    private TextView last_event;
    private TextView end_date;
    private TextView estado;

    public TaskItemLayout(Context context) {
        super(context);
        inflate(context, R.layout.list_item_task, this);

        /**
         * Es muy importante guardar las direcciones de los elementos
         * que vayamos a cambiar pues el findViewById requiere mucho tiempo
         * y si cada vez que hacemos scroll tenemos que buscar todos los elementos
         * cargaremos inecesariamente el terminal y perderemos fluidez
         */

        descripcion = (TextView) findViewById(R.id.task_item_desc);
        //last_event = (TextView) findViewById(R.id.task_item_last_event);
        fecha = (TextView) findViewById(R.id.task_item_date);
        end_date = (TextView) findViewById(R.id.task_item_end_date);
        estado = (TextView) findViewById(R.id.task_item_estado);
    }

    /**
     * Este método nos permitirá asignar los valores a los diferentes
     * componéntes gráficos según el objeto que queramos ver.
     * @param task
     */
    public void setTaskItemLayout(Task task) {
        descripcion.setText(task.getDescripcion());
        /*if (task.getLastEvent() != null) {
            last_event.setText(task.getLastEvent().toString());
        }*/
        estado.setText(task.getEstado().toString());
        fecha.setText(task.getFecha());
        String fecha_limite = task.getFecha_limite();
        if (  fecha_limite != null){
            end_date.setText(fecha_limite);
        }
    }

}