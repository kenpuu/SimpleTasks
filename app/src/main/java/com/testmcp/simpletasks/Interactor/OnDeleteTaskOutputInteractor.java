package com.testmcp.simpletasks.interactor;

import android.app.AlertDialog;

import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.view.TaskDetailFragment;
import com.testmcp.simpletasks.view.TasksListFragment;
import com.testmcp.simpletasks.view.adapter.TaskListAdapter;


/**
 * Created by mario on 28/12/2015.
 */
public class OnDeleteTaskOutputInteractor implements TasksAPI.OnDeleteTaskOutputInteractorInterface {
    TasksListFragment mFragment;
    TaskListAdapter taskListAdapter;
    public OnDeleteTaskOutputInteractor(TasksListFragment fragment) {
        this.mFragment = fragment;
    }

    // TODO Mostrar alguna alerta con fallo de login
    @Override
    public void notAllowedHere() {
        new AlertDialog.Builder(mFragment.getContext())
                .setTitle("Error de sesión")
                .setMessage("La sesión está cerrada o no se ha iniciado")
                .show();
    }

    @Override
    public void postDeleteTask() {
        mFragment.updateList();
    }
}
