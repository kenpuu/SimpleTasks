package com.testmcp.simpletasks.interactor.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.testmcp.simpletasks.view.settings.AuthPref;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
        String token = AuthPref.getToken();
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

    public static String httpDelete(URL url) throws LoginError {
        try {
            HttpsURLConnection urlConnection;
            setTrustManager();
            BufferedReader reader;
            String jsonStr;
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
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

    public static Bitmap httpGetBitmap(URL url) throws LoginError {
        Bitmap bitmap = null;
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
            InputStream in = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return bitmap;
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

    public static String httpPost(URL url, JSONObject jsonParams) throws LoginError {
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

    public static String httpUpload(URL url, Bitmap bitmap) throws LoginError {
        try {
            String attachmentName = "imagen";
            String attachmentFileName = "bitmap.bmp";
            String crlf = "\r\n";
            String twoHyphens = "--";
            String boundary =  "*****";



            HttpsURLConnection urlConnection;
            setTrustManager();
            BufferedReader reader;
            String jsonStr;
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Cache-Control", "no-cache");
            urlConnection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);
            addTokenToConnection(urlConnection);
            DataOutputStream request = new DataOutputStream(
                    urlConnection.getOutputStream());

            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" +
                    attachmentName + "\";filename=\"" +
                    attachmentFileName + "\"" + crlf);
            request.writeBytes(crlf);




            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

            request.write(bytes.toByteArray());

            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary +
                    twoHyphens + crlf);

            request.flush();
            request.close();

            //urlConnection.connect();
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

    public static String httpPut(URL url, JSONObject jsonParams) throws LoginError {
        try {
            HttpsURLConnection urlConnection;
            setTrustManager();
            BufferedReader reader;
            String jsonStr;
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("PUT");
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
