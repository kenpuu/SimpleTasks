package com.testmcp.simpletasks;

import android.util.Log;

import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.model.network.TasksAPI;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by mario on 26/12/2015.
 */
/*class LoadTasksOutputInteractorTest implements TasksAPI.LoadTasksOutputInteractor {

    @Override
    public void postGetTaskList(List<Task> taskList) {
        System.out.println("postGetTaskList");
        for (Task task:
             taskList) {
            System.out.println(task.toString());
            Log.i("postGetTaskList", task.toString());
        }
    }

    @Override
    public void postGetTask(Task task) {
        System.out.println("postGetTask");
        System.out.println(task.toString());
        Log.i("postGetTask", task.toString());
    }
}*/
/*
public class TasksAPITest extends TestCase{
    //#private final String LOG_TAG = GetForecastAsync.class.getSimpleName();

    public void testGetTasklist() throws Exception {
        TasksAPI.getTasklist();
    }

    public void testGetTask() throws Exception {
        TasksAPI.getTask(1);
    }
}*/