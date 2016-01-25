package com.testmcp.simpletasks.interactor;

import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.view.TaskDetailFragment;
import com.testmcp.simpletasks.view.adapter.TaskListAdapter;


/**
 * Created by mario on 28/12/2015.
 */
public class OnLoadTaskOutputInteractor implements TasksAPI.OnLoadTaskOutputInteractorInterface {
    TaskDetailFragment mFragment;
    TaskListAdapter taskListAdapter;
    public OnLoadTaskOutputInteractor(TaskDetailFragment fragment) {
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
