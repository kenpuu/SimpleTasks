package com.testmcp.simpletasks.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.testmcp.simpletasks.Interactor.LoadTasksOutputInteractor;
import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.model.network.TasksAPI;
import com.testmcp.simpletasks.view.adapter.TaskListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */


public class TasksListFragment extends Fragment {
    ArrayAdapter<Task> mTaskAdapter;

    public TasksListFragment() {
    }

    public void updateList() {
        TasksAPI.getTasklist(new LoadTasksOutputInteractor(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.fragment_tasks_list, container, false);
        //interactor = new LoadTasksOutputInteractor(new LoadTasksOutputInteractor());

        //TasksAPI.getTask(1, new LoadTasksOutputInteractor());
        updateList();
        return inflater.inflate(R.layout.fragment_tasks_list, container, false);
    }


}
