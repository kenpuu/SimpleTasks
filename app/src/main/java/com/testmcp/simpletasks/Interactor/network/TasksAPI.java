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

    private TasksAPI.LoadTaskOutputInteractorInterface output;
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

    public NetworkTaskGetter(TasksAPI.LoadTaskOutputInteractorInterface output) {
        this.output = output;
    }
}

class NetworkLogin extends AsyncTask<Void, Integer, String> {

    private TasksAPI.OnLoginIteractorInterface output;
    private String username;
    private String password;

    private final String tokenJsr = "token";
    @Override
    protected String doInBackground(Void... params) {

        //Uri
        // Will contain the raw JSON response as a string.
        //URL url = new URL(urls[0] + "&appid=" + appid);
        URL url = TasksURLs.getURL_get_token();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("username", username);
            jsonParam.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String taskJsonStr = NetworkGetter.httpPost(url, jsonParam, 1);
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(taskJsonStr);
                return jsonObject.getString(tokenJsr);

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } catch (LoginError loginError) {
            loginError.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String token) {
        if (token != null) output.postLogin(token);
        else output.notAllowedHere();
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

        /*//Uri
        // Will contain the raw JSON response as a string.
        //URL url = new URL(urls[0] + "&appid=" + appid);
        URL url = TasksURLs.getURL_logout();
        JSONObject jsonParam = new JSONObject();
        try {
            String taskJsonStr = NetworkGetter.httpPost(url, jsonParam, 1);
            return 0;
        } catch (LoginError loginError) {
            loginError.printStackTrace();
        } finally {

        }
        //return TaskDataParser.getTaskList(taskJsonStr);*/
        return -1;
    }

    @Override
    protected void onPostExecute(Integer i) {
        TokenAuthPref.delete();
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

class NetworkAddComment extends AsyncTask<Void, Integer, Void> {

    private TasksAPI.OnCommentAdded output;
    private int taskId;
    private String contenido;

    @Override
    protected Void doInBackground(Void... params) {


        URL url = TasksURLs.getURL_add_comment(taskId);
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("contenido", contenido);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String taskJsonStr = null;
        try {
            taskJsonStr = NetworkGetter.httpPost(url, jsonParam);
        } catch (LoginError loginError) {
            loginError.printStackTrace();
            output.notAllowedHere();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void dd) {
        super.onPostExecute(dd);
        output.postAddComment();
    }

    public NetworkAddComment(int taskId, String contenido, TasksAPI.OnCommentAdded output) {
        this.output = output;
        this.contenido = contenido;
        this.taskId = taskId;
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

    public static void addComment(int id, String contenido, OnCommentAdded onCommentAdded){
        new NetworkAddComment(id, contenido, onCommentAdded).execute();
    }

    public static void login(String username, String password, OnLoginIteractorInterface onLoginIteractorInterface){
        new NetworkLogin(username, password, onLoginIteractorInterface).execute();
    }

    public static void logout(){
        new NetworkLogout().execute();
    }

    public static void getTask(int id, LoadTaskOutputInteractorInterface loadTaskOutputInteractorInterface) {
        new NetworkTaskGetter(loadTaskOutputInteractorInterface).execute(id);
    }

    public interface OnLoginIteractorInterface extends TaskAPIInterface{
        void postLogin(String token);
    }

    interface TaskAPIInterface {
        void notAllowedHere();
    }

    public interface LoadTasksOutputInteractorInterface extends TaskAPIInterface {
        void postGetTaskList(List<Task> taskList);
    }

    public interface LoadTaskOutputInteractorInterface extends TaskAPIInterface {
        void postGetTask(Task task);
    }

    public interface OnCommentAdded extends TaskAPIInterface{
        void postAddComment();
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