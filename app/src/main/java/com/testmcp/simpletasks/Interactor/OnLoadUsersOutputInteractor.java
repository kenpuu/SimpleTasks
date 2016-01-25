package com.testmcp.simpletasks.interactor;

import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.model.User;
import com.testmcp.simpletasks.view.ShareTaskDialog;
import com.testmcp.simpletasks.view.adapter.TaskListAdapter;

import java.util.List;


/**
 * Created by mario on 28/12/2015.
 */
public class OnLoadUsersOutputInteractor implements TasksAPI.OnLoadUsersOutputInteractorInterface {
    ShareTaskDialog shareTaskDialog;
    TaskListAdapter taskListAdapter;
    public OnLoadUsersOutputInteractor(ShareTaskDialog shareTaskDialog) {
        this.shareTaskDialog = shareTaskDialog;
    }



    // TODO Mostrar alguna alerta con fallo de login
    @Override
    public void notAllowedHere() {

    }

    @Override
    public void postGetUserList(List<User> userList) {
        shareTaskDialog.setUsers(userList.toArray(new User[userList.size()]));
    }
}
