package com.testmcp.simpletasks.interactor.network;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NetworkGetter {

    private static void addTokenToConnection(HttpURLConnection urlConnection) {
        String token = TokenAuthPref.get();
        if (token != null ) urlConnection.setRequestProperty("Authorization", "Token " + token);
    }

    public static String httpGet(URL url) throws LoginError {
        try {
            HttpsURLConnection urlConnection;
            setTrustManager();
            BufferedReader reader;
            String jsonStr;
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            addTokenToConnection(urlConnection);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 400) {
                throw new LoginError();
            }
            if (urlConnection.getResponseCode() == 401) {
                throw new LoginError();
            }
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
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();

        }
        return null;
    }

    static void setTrustManager() throws NoSuchAlgorithmException, KeyManagementException {
        /* TODO Problemas con los certificados:
            Sacado de aquí:
            http://stackoverflow.com/questions/3761737/https-get-ssl-with-android-and-self-signed-server-certificate
            Pone que es inseguro */
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, new TrustManager[] {
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[]{}; }
                }
        }, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
    }

    private static void addJsonParamsToPost(HttpURLConnection urlConnection, JSONObject jsonParams) throws IOException {
        if (jsonParams.length() > 0) {
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(jsonParams.toString());
            wr.flush();
            wr.close();
        }
    }

    public static String httpPost(URL url, JSONObject jsonParams) throws LoginError{
        return httpPost(url, jsonParams, 0);
    }

    public static String httpPost(URL url, JSONObject jsonParams, int saveCookies) throws LoginError {
        try {
            HttpsURLConnection urlConnection;
            setTrustManager();
            BufferedReader reader;
            String jsonStr;
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            addTokenToConnection(urlConnection);
            addJsonParamsToPost(urlConnection, jsonParams);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 400) {
                throw new LoginError();
            }

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
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonStr = buffer.toString();
            return jsonStr;

        } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();

        }
        return null;
    }
}
