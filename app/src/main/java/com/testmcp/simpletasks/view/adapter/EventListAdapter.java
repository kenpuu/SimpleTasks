package com.testmcp.simpletasks.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.model.CambioEstado;
import com.testmcp.simpletasks.model.Comment;
import com.testmcp.simpletasks.model.TaskEvent;
// import com.testmcp.simpletasks.model.Task;

import java.security.AccessController;
import java.util.ArrayList;

//import static java.security.AccessController.getContext;

public class EventListAdapter extends BaseAdapter { //ArrayAdapter<TaskEvent> {

    private ArrayList<TaskEvent> taskEvents;

    private static final int TYPE_MAX_COUNT = 2;
    public static final int TYPE_COMMENT = 0;
    public static final int TYPE_CAMBIOESTADO = 1;

    /*public EventListAdapter(Context context, int textViewResourceId, ArrayList<TaskEvent> objects) {
        super(context, textViewResourceId, objects);
        this.taskEvents = objects;
        this.mContext = context;
    }*/

    public EventListAdapter(ArrayList<TaskEvent> taskEvents) {
        Log.i("EVENTLISTADAPTER", "Entra en EventListAdapter()");
        this.taskEvents = taskEvents;
        notifyDataSetChanged();
    }

    public int getCount() {
        return taskEvents.size();
    }

    public TaskEvent getItem(int position) {
        return taskEvents.get(position);
    }

    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Comment) return TYPE_COMMENT;
        else return TYPE_CAMBIOESTADO;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getCount() == 0 ) return convertView;
        TaskEvent taskEvent = getItem(position);
        int tipo = getItemViewType(position);
        TaskEventItemLayout layout;
        if (convertView == null) {
            if (tipo == TYPE_COMMENT) {
                Log.i("GETVIEW_EVENT", "Tipo TYPE_COMMENT");
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, null);
            } else if (tipo == TYPE_CAMBIOESTADO) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cambio_estado_layout, null);
                Log.i("GETVIEW_EVENT", "Tipo TYPE_CAMBIOESTADO");
            }
            layout = new TaskEventItemLayout(convertView, tipo);
            convertView.setTag(layout);
            //view = new TaskEventItemLayout(parent.getContext(), type);
        } else {
            //view = ((TaskEventItemLayout) convertView);
            layout = (TaskEventItemLayout) convertView.getTag();
        }

        layout.setEventTaskLayout(taskEvent);

        return convertView;
    }

}