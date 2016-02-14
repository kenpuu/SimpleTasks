package com.testmcp.simpletasks.interactor;

import android.app.AlertDialog;

import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.model.Estado;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.view.TasksListFragment;
import com.testmcp.simpletasks.view.adapter.TaskListAdapter;

import java.util.List;


/**
 * Created by mario on 28/12/2015.
 */
public class OnLoadEstadosOutputInteractor implements TasksAPI.OnLoadEstadosOutputInteractorInterface {
    TasksListFragment mFragment;
    private Task task;
    TaskListAdapter taskListAdapter;
    public OnLoadEstadosOutputInteractor(TasksListFragment fragment, Task task) {
        this.mFragment = fragment;
        this.task = task;
    }

    // TODO Mostrar alguna alerta con fallo de login
    @Override
    public void notAllowedHere() {
        new AlertDialog.Builder(mFragment.getContext())
                .setTitle("Error de sesión")
                .setMessage("La sesión está cerrada o no se ha iniciado");
    }

    @Override
    public void postGetEstadosList(List<Estado> estadoList) {
        mFragment.selectEstado(task, estadoList);
    }
}
