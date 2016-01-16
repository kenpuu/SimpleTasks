package com.testmcp.simpletasks.interactor;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.view.TaskDetail;
import com.testmcp.simpletasks.view.TasksListFragment;
import com.testmcp.simpletasks.view.adapter.TaskListAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mario on 28/12/2015.
 */
public class LoadTasksOutputInteractor implements TasksAPI.LoadTasksOutputInteractorInterface {
    TasksListFragment mFragment;
    TaskListAdapter taskListAdapter;
    public LoadTasksOutputInteractor(TasksListFragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public void postGetTaskList(List<Task> taskList) {
        try {
            ArrayList<Task> arrayAdapter = (ArrayList<Task>) taskList;
            ListView lv_tasks = (ListView) mFragment.getView().findViewById(R.id.listview_taks);
            taskListAdapter = new TaskListAdapter(arrayAdapter);
            lv_tasks.setAdapter(taskListAdapter);
            lv_tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Task task = taskListAdapter.getItem(position);
                    Intent intent = new Intent(mFragment.getContext(), TaskDetail.class)
                            .putExtra("Task", (Serializable) task);
                    mFragment.startActivity(intent);
                }
            });
            mFragment.setUpdating(false);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    // TODO Mostrar alguna alerta con fallo de login
    @Override
    public void notAllowedHere() {

    }
}
