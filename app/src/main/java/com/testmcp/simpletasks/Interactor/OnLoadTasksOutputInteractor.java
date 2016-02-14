package com.testmcp.simpletasks.interactor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.view.TasksListFragment;
import com.testmcp.simpletasks.view.adapter.TaskListAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.app.AlertDialog.*;


/**
 * Created by mario on 28/12/2015.
 */
public class OnLoadTasksOutputInteractor implements TasksAPI.OnLoadTasksOutputInteractorInterface {
    TasksListFragment mFragment;
    TaskListAdapter taskListAdapter;
    public OnLoadTasksOutputInteractor(TasksListFragment fragment) {
        this.mFragment = fragment;
    }


    private void onLongClickTask(final Task task) {
        new Builder(mFragment.getActivity())
            .setTitle(R.string.title_long_click_task_options)
            .setItems(R.array.long_click_task_options, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // The 'which' argument contains the index position
                    // of the selected item
                    if (which == 0) {
                        TasksAPI.getEstadoslist(new OnLoadEstadosOutputInteractor(mFragment, task));
                    } else {
                        new AlertDialog.Builder(mFragment.getActivity())
                            .setTitle(R.string.delete_task_confirmation_title)
                            .setMessage(R.string.delete_task_confirmation_message)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    TasksAPI.deleteTask(task.getId(), new OnDeleteTaskOutputInteractor(mFragment));
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    }
                }
            })
            .show();
    }

    @Override
    public void postGetTaskList(List<Task> taskList) {
        try {
            ArrayList<Task> arrayAdapter = (ArrayList<Task>) taskList;
            ListView lv_tasks = (ListView) mFragment.getView().findViewById(R.id.listview_taks);
            taskListAdapter = new TaskListAdapter(arrayAdapter);
            lv_tasks.setAdapter(taskListAdapter);
            lv_tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Task task = taskListAdapter.getItem(position);
                    mFragment.openTask(task);
                }
            });
            lv_tasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    onLongClickTask(taskListAdapter.getItem(position));
                    return true;
                }
            });
            /*lv_tasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Task task = taskListAdapter.getItem(position);
                    return true;
                }
            });*/
            mFragment.setUpdating(false);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    // TODO Mostrar alguna alerta con fallo de login
    @Override
    public void notAllowedHere() {
        new AlertDialog.Builder(mFragment.getContext())
            .setTitle("Error de sesión")
            .setMessage("La sesión está cerrada o no se ha iniciado")
            .show();
    }
}
