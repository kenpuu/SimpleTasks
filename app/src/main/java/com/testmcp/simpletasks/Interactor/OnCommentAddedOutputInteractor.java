package com.testmcp.simpletasks.interactor;

import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.view.TaskDetailFragment;
import com.testmcp.simpletasks.view.adapter.TaskListAdapter;


/**
 * Created by mario on 28/12/2015.
 */
public class OnCommentAddedOutputInteractor implements TasksAPI.OnCommentAdded {
    TaskDetailFragment mFragment;
    TaskListAdapter taskListAdapter;
    public OnCommentAddedOutputInteractor(TaskDetailFragment fragment) {
        this.mFragment = fragment;
    }

    // TODO Mostrar alguna alerta con fallo de login
    @Override
    public void notAllowedHere() {

    }

    @Override
    public void postAddComment() {
        mFragment.reset();
        mFragment.updateTaskDetail();
    }
}
