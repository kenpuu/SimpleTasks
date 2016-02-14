package com.testmcp.simpletasks.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.testmcp.simpletasks.interactor.OnDeleteTaskOutputInteractor;
import com.testmcp.simpletasks.interactor.OnLoadTasksOutputInteractor;
import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.interactor.OnSetTaskEstadoOutputInteractor;
import com.testmcp.simpletasks.model.Estado;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.interactor.network.TasksAPI;

import java.io.Serializable;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */


public class TasksListFragment extends Fragment {
    ArrayAdapter<Task> mTaskAdapter;

    public TasksListFragment() {
    }


    public void updateList() {
        setUpdating(true);
        TasksAPI.getTasklist(new OnLoadTasksOutputInteractor(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.fragment_tasks_list, container, false);
        //interactor = new OnLoadTasksOutputInteractor(new OnLoadTasksOutputInteractor());

        //TasksAPI.getTask(1, new OnLoadTasksOutputInteractor());
        View view = inflater.inflate(R.layout.fragment_tasks_list, container, false);
        updateList();
        //login();
        return view;
    }

    public void setUpdating(boolean updating) {
        try {
            SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_listview_taks);
            swipeContainer.setRefreshing(updating);
        } catch (NullPointerException e ){
            e.printStackTrace();
        }
    }

    public void openTask(Task task) {
        Intent intent = new Intent(getContext(), TaskDetail.class)
                .putExtra("Task", (Serializable) task);
        startActivity(intent);
    }

    public void selectEstado(final Task task, final List<Estado> estadoList) {
        String[] estados_desc = new String[estadoList.size()];
        for (int i=0; i< estadoList.size();i++) {
            estados_desc[i] = estadoList.get(i).toString();
        }
        final TasksListFragment mFragment = this;
        new AlertDialog.Builder(getActivity())
            .setTitle(R.string.title_long_click_task_options)
            .setItems(estados_desc, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    TasksAPI.setTaskEstado(task.getId(),
                                            estadoList.get(which).getId(),
                                            new OnSetTaskEstadoOutputInteractor(mFragment));
                }
            })
            .show();
    }
    
}
