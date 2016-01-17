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
            mFragment.reset();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    // TODO Mostrar alguna alerta con fallo de login
    @Override
    public void notAllowedHere() {

    }
}
