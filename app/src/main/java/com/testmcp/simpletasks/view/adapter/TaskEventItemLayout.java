package com.testmcp.simpletasks.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.model.CambioEstado;
import com.testmcp.simpletasks.model.Comment;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.model.TaskEvent;

public class TaskEventItemLayout{

    private TextView usuario;
    private TextView fecha;

    private TextView contenido;

    private TextView estado;

    public TaskEventItemLayout(View view, int tipo) {
        //super(context);
        //inflate(context, R.layout.list_item_task, this);
        fecha = (TextView) view.findViewById(R.id.taskevent_fecha);
        usuario = (TextView) view.findViewById(R.id.taskevent_usuario);
        if (tipo == EventListAdapter.TYPE_COMMENT) {
            //inflate(context, R.layout.comment_layout, this);
            contenido = (TextView) view.findViewById(R.id.comment_contenido);
        } else if (tipo == EventListAdapter.TYPE_CAMBIOESTADO) {
            //inflate(context, R.layout.cambio_estado_layout, this);
            estado = (TextView) view.findViewById(R.id.cambioestado_estado);
        }
    }

    public void setEventTaskLayout(TaskEvent taskEvent) {
        fecha.setText(taskEvent.getFecha());
        usuario.setText(taskEvent.getUsuario());
        if (taskEvent instanceof Comment) {
            Comment comment = (Comment)taskEvent;
            contenido.setText(comment.getContenido());
        } else if (taskEvent instanceof CambioEstado) {
            CambioEstado cambioEstado = (CambioEstado)taskEvent;
            estado.setText(cambioEstado.getStrEstado());
        }
    }

}