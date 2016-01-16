package com.testmcp.simpletasks.interactor;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.model.TaskEvent;
import com.testmcp.simpletasks.view.TaskDetail;
import com.testmcp.simpletasks.view.TaskDetailFragment;
import com.testmcp.simpletasks.view.TasksListFragment;
import com.testmcp.simpletasks.view.adapter.EventListAdapter;
import com.testmcp.simpletasks.view.adapter.TaskListAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mario on 28/12/2015.
 */
public class LoadTaskOutputInteractor implements TasksAPI.LoadTaskOutputInteractorInterface {
    TaskDetailFragment mFragment;
    TaskListAdapter taskListAdapter;
    public LoadTaskOutputInteractor(TaskDetailFragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public void postGetTask(Task task) {
        try {
            mFragment.setTask(task);
            ((TextView) mFragment.getActivity().findViewById(R.id.task_detail_desc))
                    .setText(task.getDescripcion());
            ArrayList<TaskEvent> taskEvents = task.getTaskEvents();
            ListView listView = (ListView) mFragment.getActivity().findViewById(R.id.event_list);
            EventListAdapter eventListAdapter = new EventListAdapter(taskEvents);
            listView.setAdapter(eventListAdapter);
            Button button = (Button) mFragment.getActivity().findViewById(R.id.add_comment_button);
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    EditText editText = (EditText) mFragment.getActivity().findViewById(R.id.comment_edit_text);
                    String contenido = editText.getText().toString();
                    TasksAPI.addComment(mFragment.getTask().getId(), contenido, new OnCommentAddedOutputInteractor(mFragment));
                }
            });
            //TextView t = (TextView) getView().findViewById(R.id.text_view_task);
            //t.setText(task.toString());
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    // TODO Mostrar alguna alerta con fallo de login
    @Override
    public void notAllowedHere() {

    }
}
