package com.testmcp.simpletasks.interactor.network;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.testmcp.simpletasks.model.Estado;
import com.testmcp.simpletasks.model.Imagen;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TasksAPI {

    public static void getTasklist(OnLoadTasksOutputInteractorInterface onLoadTasksOutputInteractorInterface){
        new NetworkTaskListGetter(onLoadTasksOutputInteractorInterface).execute();
    }

    public static void getUserlist(Task task, String username, OnLoadUsersOutputInteractorInterface onLoadUsersOutputInteractorInterface){
        new NetworkUserListGetter(task, username, onLoadUsersOutputInteractorInterface).execute();
    }

    public static void getEstadoslist(OnLoadEstadosOutputInteractorInterface onLoadEstadosOutputInteractorInterface){
        new NetworkEstadosListGetter(onLoadEstadosOutputInteractorInterface).execute();
    }

    public static void addTask(String descripcion, OnTaskAdded onTaskAdded){
        new NetworkAddTask(descripcion, onTaskAdded).execute();
    }

    public static void asingUsers(Task task, int[] userIDs, OnAsignedUsers onAsignedUsers){
        new NetworkAsingUsers(task, userIDs, onAsignedUsers).execute();
    }

    public static void addComment(int id, String contenido, OnCommentAdded onCommentAdded){
        new NetworkAddComment(id, contenido, onCommentAdded).execute();
    }

    public static void setTaskEstado(int taskId, int estadoId, OnSetTaskEstadoInteractorInterface onSetEstado){
        new NetworkSetTaskEstado(taskId, estadoId, onSetEstado).execute();
    }

    public static void addImage(int id, Bitmap bitmap, OnImageAdded onImageAdded){
        new NetworkAddImage(id, bitmap, onImageAdded).execute();
    }

    public static void login(String username, String password, OnLoginIteractorInterface onLoginIteractorInterface){
        new NetworkLogin(username, password, onLoginIteractorInterface).execute();
    }

    public static void logout(OnLogoutIteractorInterface onLogoutIteractorInterface){
        new NetworkLogout(onLogoutIteractorInterface).execute();
    }

    public static void getTask(int id, OnLoadTaskOutputInteractorInterface onLoadTaskOutputInteractorInterface) {
        new NetworkTaskGetter(onLoadTaskOutputInteractorInterface).execute(id);
    }

    public static void deleteTask(int id, OnDeleteTaskOutputInteractorInterface onDeleteTaskOutputInteractorInterface) {
        new NetworkTaskDeleter(onDeleteTaskOutputInteractorInterface).execute(id);
    }

    public static void getImgTask(Imagen imagen){
        getImgTask(imagen, false);
    }

    public static void getImgTask(Imagen imagen, boolean noThreaded){
        if(noThreaded) {
            new NetworkGetImagenNoThread(imagen).getImagen();
        } else {
            new NetworkGetImagen(imagen).execute();
        }
    }

    public interface OnLoginIteractorInterface extends TaskAPIInterface{
        void postLogin(String token);
    }

    public interface OnLoadImagenInterface extends TaskAPIInterface{
        void postDownloaded(Bitmap bitmap);
    }

    interface TaskAPIInterface {
        void notAllowedHere();
    }

    public interface OnLoadTasksOutputInteractorInterface extends TaskAPIInterface {
        void postGetTaskList(List<Task> taskList);
    }

    public interface OnLoadEstadosOutputInteractorInterface extends TaskAPIInterface {
        void postGetEstadosList(List<Estado> estadoList);
    }

    public interface OnLoadUsersOutputInteractorInterface extends TaskAPIInterface {
        void postGetUserList(List<User> taskList);
    }

    public interface OnLoadTaskOutputInteractorInterface extends TaskAPIInterface {
        void postGetTask(Task task);
    }

    public interface OnDeleteTaskOutputInteractorInterface extends TaskAPIInterface {
        void postDeleteTask();
    }

    public interface OnSetTaskEstadoInteractorInterface extends TaskAPIInterface {
        void postSetTaskEstado();
    }

    public interface OnCommentAdded extends TaskAPIInterface{
        void postAddComment();
    }

    public interface OnImageAdded extends TaskAPIInterface{
        void postAddImage();
    }

    public interface OnTaskAdded extends TaskAPIInterface{
        void postAddTask();
    }

    public interface OnAsignedUsers extends TaskAPIInterface{
        void postAsignedUsers(int[] userIDs);
    }

    public interface OnLogoutIteractorInterface {
        void postLogout();
    }
}

class NetworkTaskGetter extends AsyncTask<Integer, Integer, Task> {

    private TasksAPI.OnLoadTaskOutputInteractorInterface output;
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

    public NetworkTaskGetter(TasksAPI.OnLoadTaskOutputInteractorInterface output) {
        this.output = output;
    }
}

class NetworkTaskDeleter extends AsyncTask<Integer, Integer, Boolean> {

    private TasksAPI.OnDeleteTaskOutputInteractorInterface output;
    @Override
    protected Boolean doInBackground(Integer... params) {

        //Uri
        // Will contain the raw JSON response as a string.
        //URL url = new URL(urls[0] + "&appid=" + appid);
        int id = params[0];
        URL url = TasksURLs.getURL_task(id);
        String taskJsonStr = null;
        try {
            taskJsonStr = NetworkGetter.httpDelete(url);
        } catch (LoginError loginError) {
            loginError.printStackTrace();
            output.notAllowedHere();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean done) {
        //super.onPostExecute(tasks);
        if (done) {
            output.postDeleteTask();
        }
    }

    public NetworkTaskDeleter(TasksAPI.OnDeleteTaskOutputInteractorInterface output) {
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
        } catch (JSONException|NullPointerException e) {
            e.printStackTrace();
        }

        try {
            String taskJsonStr = NetworkGetter.httpPost(url, jsonParam);
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(taskJsonStr);
                return jsonObject.getString(tokenJsr);

            } catch (JSONException|NullPointerException e) {
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
    TasksAPI.OnLogoutIteractorInterface output;
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
        output.postLogout();
    }

    public NetworkLogout(TasksAPI.OnLogoutIteractorInterface output) {
        this.output = output;
    }
}

class NetworkTaskListGetter extends AsyncTask<Void, Integer, List<Task>> {

    private TasksAPI.OnLoadTasksOutputInteractorInterface output;
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
            //output.notAllowedHere();
        }
        if (taskJsonStr != null)
            return TaskDataParser.getTaskList(taskJsonStr);
        else return null;
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        //super.onPostExecute(tasks);
        if (tasks != null) {
            output.postGetTaskList(tasks);
        } else {
            output.notAllowedHere();
        }
    }

    public NetworkTaskListGetter(TasksAPI.OnLoadTasksOutputInteractorInterface output) {
        this.output = output;
    }
}

class NetworkGetImagenNoThread {
    Imagen imagen;

    public NetworkGetImagenNoThread(Imagen imagen) {
        this.imagen = imagen;
    }

    public void getImagen(){
        URL url = TasksURLs.getURL_from_string(imagen.getImagenUrlStr());
        Bitmap bitmap = null;
        try {
            bitmap = NetworkGetter.httpGetBitmap(url);
        } catch (LoginError loginError) {
            loginError.printStackTrace();
            //output.notAllowedHere();
        }

        if (bitmap != null) {
            //output.postDownloaded(bitmap);
            imagen.setImagenBitmap(bitmap);
        }
    }
}

class NetworkGetImagen extends AsyncTask<Void, Integer, Bitmap> {
    Imagen imagen;
    //private TasksAPI.OnLoadImagenInterface output;

    public NetworkGetImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        //Uri
        // Will contain the raw JSON response as a string.
        //URL url = new URL(urls[0] + "&appid=" + appid);
        //URL url = TasksURLs.getURL_list_tasks();
        URL url = TasksURLs.getURL_from_string(imagen.getImagenUrlStr());
        Bitmap bitmap = null;
        try {
            bitmap = NetworkGetter.httpGetBitmap(url);
        } catch (LoginError loginError) {
            loginError.printStackTrace();
            //output.notAllowedHere();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            //output.postDownloaded(bitmap);
            imagen.setImagenBitmap(bitmap);
        }
    }
}

class NetworkUserListGetter extends AsyncTask<Void, Integer, List<User>> {

    private final Task task;
    private final String username;
    private TasksAPI.OnLoadUsersOutputInteractorInterface output;
    @Override
    protected List<User> doInBackground(Void... params) {

        //Uri
        // Will contain the raw JSON response as a string.
        //URL url = new URL(urls[0] + "&appid=" + appid);
        URL url = TasksURLs.getURL_list_users(task, username);
        String userListJsonStr = null;
        try {
            userListJsonStr = NetworkGetter.httpGet(url);
        } catch (LoginError loginError) {
            loginError.printStackTrace();
            output.notAllowedHere();
        }
        if (userListJsonStr != null)
            return TaskDataParser.getUserList(userListJsonStr);
        else return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<User> users) {
        //super.onPostExecute(tasks);
        if (users != null) {
            output.postGetUserList(users);
        }
    }

    public NetworkUserListGetter(Task task, String username, TasksAPI.OnLoadUsersOutputInteractorInterface output) {
        this.task = task;
        this.output = output;
        this.username = username;
    }
}

class NetworkEstadosListGetter extends AsyncTask<Void, Integer, List<Estado>> {

    private TasksAPI.OnLoadEstadosOutputInteractorInterface output;
    @Override
    protected List<Estado> doInBackground(Void... params) {

        //Uri
        // Will contain the raw JSON response as a string.
        //URL url = new URL(urls[0] + "&appid=" + appid);
        URL url = TasksURLs.getURL_list_estados();
        String estadosListJsonStr = null;
        try {
            estadosListJsonStr = NetworkGetter.httpGet(url);
        } catch (LoginError loginError) {
            loginError.printStackTrace();
            output.notAllowedHere();
        }
        if (estadosListJsonStr != null)
            return TaskDataParser.getEstadosList(estadosListJsonStr);
        else return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Estado> estados) {
        //super.onPostExecute(tasks);
        if (estados != null) {
            output.postGetEstadosList(estados);
        }
    }

    public NetworkEstadosListGetter(TasksAPI.OnLoadEstadosOutputInteractorInterface output) {
        this.output = output;
    }
}

class NetworkAddComment extends AsyncTask<Void, Integer, String> {

    private TasksAPI.OnCommentAdded output;
    private int taskId;
    private String contenido;

    @Override
    protected String doInBackground(Void... params) {


        URL url = TasksURLs.getURL_add_comment(taskId);
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("contenido", contenido);
        } catch (JSONException|NullPointerException e) {
            e.printStackTrace();
        }
        try {
            return NetworkGetter.httpPost(url, jsonParam);
        } catch (LoginError loginError) {
            loginError.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String dd) {
        super.onPostExecute(dd);
        if (dd == null){
            output.notAllowedHere();
        } else {
            output.postAddComment();
        }
    }

    public NetworkAddComment(int taskId, String contenido, TasksAPI.OnCommentAdded output) {
        this.output = output;
        this.contenido = contenido;
        this.taskId = taskId;
    }
}

class NetworkSetTaskEstado extends AsyncTask<Void, Integer, String> {

    private int estadoId;
    private TasksAPI.OnSetTaskEstadoInteractorInterface output;
    private int taskId;

    @Override
    protected String doInBackground(Void... params) {


        URL url = TasksURLs.getURL_task(taskId);
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("estado", estadoId);
        } catch (JSONException|NullPointerException e) {
            e.printStackTrace();
        }
        try {
            return NetworkGetter.httpPut(url, jsonParam);
        } catch (LoginError loginError) {
            loginError.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String dd) {
        super.onPostExecute(dd);
        if (dd == null){
            output.notAllowedHere();
        } else {
            output.postSetTaskEstado();
        }
    }

    public NetworkSetTaskEstado(int taskId, int estadoId, TasksAPI.OnSetTaskEstadoInteractorInterface output) {
        this.estadoId = estadoId;
        this.output = output;
        this.taskId = taskId;
    }
}

class NetworkAddImage extends AsyncTask<Void, Integer, String> {

    private TasksAPI.OnImageAdded output;
    private int taskId;
    private Bitmap bitmap;

    @Override
    protected String doInBackground(Void... params) {


        URL url = TasksURLs.getURL_add_image(taskId);
        JSONObject jsonParam = new JSONObject();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        try {
            jsonParam.put("imagen", bytes);
        } catch (JSONException|NullPointerException e) {
            e.printStackTrace();
        }
        try {
            return NetworkGetter.httpUpload(url, bitmap);
        } catch (LoginError loginError) {
            loginError.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String dd) {
        super.onPostExecute(dd);
        if (dd == null){
            output.notAllowedHere();
        } else {
            output.postAddImage();
        }
    }

    public NetworkAddImage(int taskId, Bitmap bitmap, TasksAPI.OnImageAdded output) {
        this.output = output;
        this.bitmap = bitmap;
        this.taskId = taskId;
    }
}

class NetworkAddTask extends AsyncTask<Void, Integer, String> {

    private TasksAPI.OnTaskAdded output;
    private String descripcion;

    @Override
    protected String doInBackground(Void... params) {


        URL url = TasksURLs.getURL_list_tasks();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("descripcion", descripcion);
            jsonParam.put("estado", 1);
        } catch (JSONException|NullPointerException e) {
            e.printStackTrace();
        }
        String taskJsonStr = null;
        try {
            return NetworkGetter.httpPost(url, jsonParam);
        } catch (LoginError loginError) {
            loginError.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(String dd) {
        super.onPostExecute(dd);
        if (dd == null){
            output.notAllowedHere();
        } else {
            output.postAddTask();
        }
    }

    public NetworkAddTask(String descripcion, TasksAPI.OnTaskAdded output) {
        this.output = output;
        this.descripcion = descripcion;
    }
}

class NetworkAsingUsers extends AsyncTask<Void, Integer, String> {

    private Task task;
    private int[] userIDs;
    private TasksAPI.OnAsignedUsers output;
    private String descripcion;

    @Override
    protected String doInBackground(Void... params) {


        URL url = TasksURLs.getURL_task(task.getId());
        JSONObject jsonParam = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i< userIDs.length; i++) jsonArray.put(userIDs[i]);
        try {
            jsonParam.put("usuarios_asignados", jsonArray);
        } catch (JSONException|NullPointerException e) {
            e.printStackTrace();
        }
        String taskJsonStr = null;
        try {
            return NetworkGetter.httpPut(url, jsonParam);
        } catch (LoginError loginError) {
            loginError.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(String dd) {
        super.onPostExecute(dd);
        if (dd == null){
            output.notAllowedHere();
        } else {
            output.postAsignedUsers(userIDs);
        }
    }

    public NetworkAsingUsers(Task task, int[] userIDs, TasksAPI.OnAsignedUsers output) {
        this.task = task;
        this.userIDs = userIDs;
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
        } catch (JSONException|NullPointerException e) {
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
        } catch (JSONException|NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<User> getUserList(String userListJsonStr) {
        List<User> userList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(userListJsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                userList.add(new User(jsonArray.getJSONObject(i)));
            }
            return userList;
        } catch (JSONException|NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Estado> getEstadosList(String estadosListJsonStr) {
        List<Estado> estados = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(estadosListJsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                estados.add(new Estado(jsonArray.getJSONObject(i)));
            }
            return estados;
        } catch (JSONException|NullPointerException e) {
            e.printStackTrace();
            return null;
        }
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