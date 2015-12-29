package com.testmcp.simpletasks.Interactor;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.model.network.TasksAPI;
import com.testmcp.simpletasks.view.TaskDetail;
import com.testmcp.simpletasks.view.adapter.TaskListAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mario on 28/12/2015.
 */
public class LoadTasksOutputInteractor implements TasksAPI.LoadTasksOutputInteractorInterface {
    Fragment mFragment;
    TaskListAdapter taskListAdapter;
    public LoadTasksOutputInteractor(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public void postGetTaskList(List<Task> taskList) {
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
    }

    @Override
    public void postGetTask(Task task) {
        try {
            //TextView t = (TextView) getView().findViewById(R.id.text_view_task);
            //t.setText(task.toString());
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
