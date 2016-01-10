package com.testmcp.simpletasks.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.testmcp.simpletasks.interactor.LoadTasksOutputInteractor;
import com.testmcp.simpletasks.interactor.OnLoginOuputInteractor;
import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.interactor.network.TasksAPI;

/**
 * A placeholder fragment containing a simple view.
 */


public class TasksListFragment extends Fragment {
    ArrayAdapter<Task> mTaskAdapter;

    public TasksListFragment() {
    }


    public void updateList() {
        setUpdating(true);
        TasksAPI.getTasklist(new LoadTasksOutputInteractor(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.fragment_tasks_list, container, false);
        //interactor = new LoadTasksOutputInteractor(new LoadTasksOutputInteractor());

        //TasksAPI.getTask(1, new LoadTasksOutputInteractor());
        //updateList();
        //login();
        return inflater.inflate(R.layout.fragment_tasks_list, container, false);
    }

    public void login() {
        TasksAPI.login(new OnLoginOuputInteractor());
    }


    public void setUpdating(boolean updating) {
        try {
            SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_listview_taks);
            swipeContainer.setRefreshing(updating);
        } catch (NullPointerException e ){
            e.printStackTrace();
        }
    }
}
