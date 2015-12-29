package com.testmcp.simpletasks.model.network;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.testmcp.simpletasks.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 26/12/2015.
 */

class TasksURLs {
    private static String domain = "tasks.testmcp.com";
    private static String[] basepaths = {"tareas", "api"};

    protected static Uri.Builder getBaseUriBuilder (){
        Uri.Builder uri_builder = new Uri.Builder();
        uri_builder.scheme("http").authority(domain);
        for (String path: basepaths) {
            uri_builder.appendPath(path);
        }
        return uri_builder;
    }
    // http://task.testmcp.com/tareas/api/
    protected static URL getURL_list_tasks() {
        try {
            return new URL(getBaseUriBuilder().build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static URL getURL_task(int id) {
        try {
            Uri.Builder base_builder = getBaseUriBuilder();
            base_builder.appendPath(Integer.toString(id));
            String urlStr = base_builder.build().toString();
            URL url = new URL(urlStr);
            return url;
        } catch (MalformedURLException e) {
            Log.e("URL ERROR", e.toString());
            e.printStackTrace();
            return null;
        }
    }
}

class NetworkGetter {
    public static String httpGet(URL url) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonStr;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonStr = buffer.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}


class NetworkTaskGetter extends AsyncTask<Integer, Integer, Task> {

    private TasksAPI.LoadTasksOutputInteractorInterface output;
    @Override
    protected Task doInBackground(Integer... params) {

        //Uri
        // Will contain the raw JSON response as a string.
        //URL url = new URL(urls[0] + "&appid=" + appid);
        int id = params[0];
        URL url = TasksURLs.getURL_task(id);
        String taskJsonStr = NetworkGetter.httpGet(url);
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

class NetworkTaskListGetter extends AsyncTask<Void, Integer, List<Task>> {

    private TasksAPI.LoadTasksOutputInteractorInterface output;
    @Override
    protected List<Task> doInBackground(Void... params) {

        //Uri
        // Will contain the raw JSON response as a string.
        //URL url = new URL(urls[0] + "&appid=" + appid);
        URL url = TasksURLs.getURL_list_tasks();
        String taskJsonStr = NetworkGetter.httpGet(url);
        return TaskDataParser.getTaskList(taskJsonStr);
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
        JSONObject jsonObject = null;
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

    public static void getTask(int id, LoadTasksOutputInteractorInterface loadTasksOutputInteractorInterface) {
        new NetworkTaskGetter(loadTasksOutputInteractorInterface).execute(id);
    }

    public interface LoadTasksOutputInteractorInterface {
        void postGetTaskList(List<Task> taskList);

        void postGetTask(Task task);
    }
}


