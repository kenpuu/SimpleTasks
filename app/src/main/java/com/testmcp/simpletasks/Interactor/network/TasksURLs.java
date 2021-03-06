package com.testmcp.simpletasks.interactor.network;

import android.net.Uri;
import android.util.Log;

import com.testmcp.simpletasks.model.Task;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by mario on 26/12/2015.
 */

public class TasksURLs {
    private static String domain = "tasks.testmcp.com";
    private static String[] base_task_api_paths = {"tareas", "api"};
    private static String[] base_perm_api_paths = {"permisos", "api"};
    private static String add_comment = "comentario";
    private static String estado = "estado";
    private static String find_users = "find_users";
    private static String base_auth_path = "rest-auth";
    private static String get_token_path = "api-token-auth";
    private static String scheme = "https";

    protected static URI getBaseURI() {
        try {
            return new URI(getBaseTaskAPIUriBuilder().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static Uri.Builder getBaseTaskAPIUriBuilder(){
        Uri.Builder uri_builder = new Uri.Builder();
        uri_builder.scheme(scheme).authority(domain);
        for (String path: base_task_api_paths) {
            uri_builder.appendPath(path).appendPath("");
        }
        return uri_builder;
    }

    protected static Uri.Builder getBasePermAPIUriBuilder(){
        Uri.Builder uri_builder = new Uri.Builder();
        uri_builder.scheme(scheme).authority(domain);
        for (String path: base_perm_api_paths) {
            uri_builder.appendPath(path).appendPath("");
        }
        return uri_builder;
    }

    protected static Uri.Builder getLoginAPIUriBuilder(){
        Uri.Builder uri_builder = new Uri.Builder();
        uri_builder.scheme(scheme).authority(domain);
        uri_builder.appendPath(base_auth_path);
        String auth_login_path = "login";
        uri_builder.appendPath(auth_login_path).appendPath("");
        return uri_builder;
    }

    protected static Uri.Builder getTokenAPIUriBuilder(){
        Uri.Builder uri_builder = new Uri.Builder();
        uri_builder.scheme(scheme).authority(domain);
        uri_builder.appendPath(get_token_path).appendPath("");
        return uri_builder;
    }

    protected static Uri.Builder getLogoutAPIUriBuilder(){
        Uri.Builder uri_builder = new Uri.Builder();
        uri_builder.scheme(scheme).authority(domain);
        uri_builder.appendPath(base_auth_path);
        String auth_logout_path = "logout";
        uri_builder.appendPath(auth_logout_path).appendPath("");
        return uri_builder;
    }

    // http://task.testmcp.com/tareas/api/
    protected static URL getURL_list_tasks() {
        try {
            return new URL(getBaseTaskAPIUriBuilder().build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static URL getURL_task(int id) {
        try {
            Uri.Builder base_builder = getBaseTaskAPIUriBuilder();
            base_builder.appendPath(Integer.toString(id)).appendPath("");
            String urlStr = base_builder.build().toString();
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            Log.e("URL ERROR", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    protected static URL getURL_login() {
        try {
            Uri.Builder base_builder = getLoginAPIUriBuilder();
            String urlStr = base_builder.build().toString();
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            Log.e("URL ERROR", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    protected static URL getURL_get_token() {
        try {
            Uri.Builder base_builder = getTokenAPIUriBuilder();
            String urlStr = base_builder.build().toString();
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            Log.e("URL ERROR", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    protected static URL getURL_logout() {
        try {
            Uri.Builder base_builder = getLogoutAPIUriBuilder();
            String urlStr = base_builder.build().toString();
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            Log.e("URL ERROR", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    public static URL getURL_add_comment(int id) {
        try {
            Uri.Builder base_builder = getBaseTaskAPIUriBuilder();
            base_builder.appendPath(Integer.toString(id)).appendPath("comentario").appendPath("");
            String urlStr = base_builder.build().toString();
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            Log.e("URL ERROR", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    public static URL getURL_list_users(Task task, String username) {
        try {
            Uri.Builder base_builder = getBaseTaskAPIUriBuilder();
            base_builder.appendPath(Integer.toString(task.getId()))
                    .appendPath(find_users)
                    .appendPath(username)
                    .appendPath("");
            if(username.length() == 0) base_builder.appendEncodedPath("/");
            String urlStr = base_builder.build().toString();
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            Log.e("URL ERROR", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    public static  URL getURL_from_string(String urlStr){
        try {
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            Log.e("URL ERROR", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    public static URL getURL_add_image(int taskId) {
        try {
            Uri.Builder base_builder = getBaseTaskAPIUriBuilder();
            base_builder.appendPath(Integer.toString(taskId)).appendPath("imagen").appendPath("");
            String urlStr = base_builder.build().toString();
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            Log.e("URL ERROR", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    public static URL getURL_list_estados() {
        try {
            Uri.Builder base_builder = getBaseTaskAPIUriBuilder();
            base_builder.appendPath(estado).appendPath("");
            String urlStr = base_builder.build().toString();
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            Log.e("URL ERROR", e.toString());
            e.printStackTrace();
            return null;
        }
    }
}
