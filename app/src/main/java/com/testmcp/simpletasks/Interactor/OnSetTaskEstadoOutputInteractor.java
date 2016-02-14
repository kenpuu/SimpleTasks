package com.testmcp.simpletasks.interactor;

import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.view.TasksListFragment;
import com.testmcp.simpletasks.view.adapter.TaskListAdapter;


/**
 * Created by mario on 28/12/2015.
 */
public class OnSetTaskEstadoOutputInteractor implements TasksAPI.OnSetTaskEstadoInteractorInterface {
    TasksListFragment mFragment;
    TaskListAdapter taskListAdapter;
    public OnSetTaskEstadoOutputInteractor(TasksListFragment fragment) {
        this.mFragment = fragment;
    }

    // TODO Mostrar alguna alerta con fallo de login
    @Override
    public void notAllowedHere() {

    }

    @Override
    public void postSetTaskEstado() {
        mFragment.updateList();
    }
}
