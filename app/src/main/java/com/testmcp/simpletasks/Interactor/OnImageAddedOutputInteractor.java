package com.testmcp.simpletasks.interactor;

import android.app.AlertDialog;

import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.view.TaskDetailFragment;
import com.testmcp.simpletasks.view.adapter.TaskListAdapter;


/**
 * Created by mario on 28/12/2015.
 */
public class OnImageAddedOutputInteractor implements TasksAPI.OnImageAdded {
    TaskDetailFragment mFragment;
    TaskListAdapter taskListAdapter;

    public OnImageAddedOutputInteractor(TaskDetailFragment fragment) {
        this.mFragment = fragment;
    }

    // TODO Mostrar alguna alerta con fallo de login
    @Override
    public void notAllowedHere() {
        new AlertDialog.Builder(mFragment.getContext())
                .setTitle("Error de sesión")
                .setMessage("La sesión está cerrada o no se ha iniciado");
    }

    @Override
    public void postAddImage() {
        mFragment.reset();
        mFragment.updateTaskDetail();
    }
}
