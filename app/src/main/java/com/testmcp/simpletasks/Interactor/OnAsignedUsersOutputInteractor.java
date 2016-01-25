package com.testmcp.simpletasks.interactor;

import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.view.TaskDetailFragment;
import com.testmcp.simpletasks.view.TasksListFragment;


/**
 * Created by mario on 28/12/2015.
 */
public class OnAsignedUsersOutputInteractor implements TasksAPI.OnAsignedUsers {
    TaskDetailFragment mFragment;

    public OnAsignedUsersOutputInteractor(TaskDetailFragment fragment) {
        this.mFragment = fragment;
    }

    // TODO Mostrar alguna alerta con fallo de login
    @Override
    public void notAllowedHere() {

    }

    @Override
    public void postAsignedUsers(int[] userIDs) {
        mFragment.getTask().setIdsUsuariosAsignados(userIDs);
    }
}
