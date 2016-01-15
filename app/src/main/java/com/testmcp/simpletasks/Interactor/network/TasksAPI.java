package com.testmcp.simpletasks.interactor.network;

import android.os.AsyncTask;

import com.testmcp.simpletasks.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class NetworkTaskGetter extends AsyncTask<Integer, Integer, Task> {

    private TasksAPI.LoadTasksOutputInteractorInterface output;
    @Override
    protected Task doInBackground(Integer... params) {

        //Uri
        // Will contain the raw JSON response as a string.
        //URL url = new URL(urls[0] + "&appid=" + appid);
        int id = params[0];
        URL url = TasksURLs.getURL_task(id);
        String taskJsonStr = null;
        try {
            taskJsonStr = NetworkGetter.httpGet(url);
        } catch (LoginError loginError) {
            loginError.printStackTrace();
            output.notAllowedHere();
        }
        return TaskDataParser.getTask(taskJsonStr);
    }

    @Override
    protected void onPostExecute(Task task) {
        //super.onPostExecute(tasks);
        if (task != null) {
            output.postGetTask(task);
        }
    }

    public NetworkTaskGetter(TasksAPI.LoadTasksOutputInteractorInterface output) {
        this.output = output;
    }
}

class NetworkLogin extends AsyncTask<Void, Integer, Integer> {

    private TasksAPI.OnLoginIteractorInterface output;
    private String username;
    private String password;
    @Override
    protected Integer doInBackground(Void... params) {

        //Uri
        // Will contain the raw JSON response as a string.
        //URL url = new URL(urls[0] + "&appid=" + appid);
        URL url = TasksURLs.getURL_login();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("username", username);
            jsonParam.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String taskJsonStr = NetworkGetter.httpPost(url, jsonParam, 1);
            return 0;
        } catch (LoginError loginError) {
            loginError.printStackTrace();
        }
        //return TaskDataParser.getTaskList(taskJsonStr);
        return -1;
    }

    @Override
    protected void onPostExecute(Integer i) {
        if (i == 0) output.postLogin();
        else output.loginFailed();
    }

    public NetworkLogin(String username, String password, TasksAPI.OnLoginIteractorInterface output) {
        this.username = username;
        this.password = password;
        this.output = output;
    }
}

class NetworkLogout extends AsyncTask<Void, Void, Integer> {

    @Override
    protected Integer doInBackground(Void... params) {

        //Uri
        // Will contain the raw JSON response as a string.
        //URL url = new URL(urls[0] + "&appid=" + appid);
        URL url = TasksURLs.getURL_logout();
        JSONObject jsonParam = new JSONObject();
        /*try {
            jsonParam.put("username", username);
            jsonParam.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        try {
            String taskJsonStr = NetworkGetter.httpPost(url, jsonParam, 1);
            return 0;
        } catch (LoginError loginError) {
            loginError.printStackTrace();
        } finally {
            NetworkGetter.removeCookies();
        }
        //return TaskDataParser.getTaskList(taskJsonStr);
        return -1;
    }

    @Override
    protected void onPostExecute(Integer i) {
        NetworkGetter.printCookies("onPostExecute Logout: ");
    }

    public NetworkLogout() {
    }
}

class NetworkTaskListGetter extends AsyncTask<Void, Integer, List<Task>> {

    private TasksAPI.LoadTasksOutputInteractorInterface output;
    @Override
    protected List<Task> doInBackground(Void... params) {

        //Uri
        // Will contain the raw JSON response as a string.
        //URL url = new URL(urls[0] + "&appid=" + appid);
        URL url = TasksURLs.getURL_list_tasks();
        String taskJsonStr = null;
        try {
            taskJsonStr = NetworkGetter.httpGet(url);
        } catch (LoginError loginError) {
            loginError.printStackTrace();
            output.notAllowedHere();
        }
        if (taskJsonStr != null)
            return TaskDataParser.getTaskList(taskJsonStr);
        else return new ArrayList<Task>();
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        //super.onPostExecute(tasks);
        if (tasks != null) {
            output.postGetTaskList(tasks);
        }
    }

    public NetworkTaskListGetter(TasksAPI.LoadTasksOutputInteractorInterface output) {
        this.output = output;
    }
}

class TaskDataParser {
    public static double getMinTemperatureForDay(String weatherJsonStr, int dayIndex)
            throws JSONException {
        JSONObject jsonObject = new JSONObject(weatherJsonStr);
        JSONArray list = jsonObject.getJSONArray("list");
        JSONObject day = list.getJSONObject(dayIndex);
        JSONObject temp = day.getJSONObject("temp");
        return temp.getLong("min");
    }

    public static Task getTask(String taskJsonStr) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(taskJsonStr);
            return new Task(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Task> getTaskList(String taskListJsonStr){
        List<Task> taskList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(taskListJsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                taskList.add(new Task(jsonArray.getJSONObject(i)));
            }
            return taskList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

public class TasksAPI {

    public static void getTasklist(LoadTasksOutputInteractorInterface loadTasksOutputInteractorInterface){
        new NetworkTaskListGetter(loadTasksOutputInteractorInterface).execute();
    }

    public static void login(String username, String password, OnLoginIteractorInterface onLoginIteractorInterface){
        new NetworkLogin(username, password, onLoginIteractorInterface).execute();
    }

    public static void logout(){
        new NetworkLogout().execute();
    }

    public static void getTask(int id, LoadTasksOutputInteractorInterface loadTasksOutputInteractorInterface) {
        new NetworkTaskGetter(loadTasksOutputInteractorInterface).execute(id);
    }

    public interface OnLoginIteractorInterface {
        void postLogin();

        void loginFailed();
    }

    public interface LoadTasksOutputInteractorInterface {
        void postGetTaskList(List<Task> taskList);

        void postGetTask(Task task);

        void notAllowedHere();
    }
}

class LoginError extends Exception {
    @Override
    public String toString() {
        return super.toString();
    }
}

class NoSessionError extends Exception {
    @Override
    public String toString() {
        return super.toString();
    }
}