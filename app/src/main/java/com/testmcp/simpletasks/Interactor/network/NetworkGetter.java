package com.testmcp.simpletasks.interactor.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NetworkGetter {
    //static java.net.CookieManager mCookieManager = new java.net.CookieManager();
    static java.net.CookieManager msCookieManager;
    static final String COOKIES_HEADER = "Set-Cookie";

    public static void setCookieManager(Context context) {
        SiCookieStore2 siCookieStore = new SiCookieStore2(context);
        msCookieManager = new CookieManager((CookieStore) siCookieStore, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(msCookieManager);
        if (msCookieManager.getCookieStore().getCookies().size() > 0) {
            //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
            CookieStore cookieStore = msCookieManager.getCookieStore();
            for (HttpCookie cookie : cookieStore.getCookies()) {
                Log.i("setCookieManager", cookie.toString());
            }
        }
    }
    // http://stackoverflow.com/questions/16150089/how-to-handle-cookies-in-httpurlconnection-using-cookiemanager
    private static void setCookies(HttpsURLConnection connection) {
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
        if(cookiesHeader != null)
        {
            for (String cookie : cookiesHeader)
            {
                msCookieManager.getCookieStore().add(TasksURLs.getBaseURI(), HttpCookie.parse(cookie).get(0));
            }
        }
    }

    public static void removeCookies() {
        msCookieManager.getCookieStore().removeAll();
    }

    static void printCookies(String TAG) {
        if (msCookieManager.getCookieStore().getCookies().size() > 0) {
            //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
            CookieStore cookieStore = msCookieManager.getCookieStore();
            for (HttpCookie cookie : cookieStore.getCookies()) {
                Log.i(TAG, cookie.toString());
            }
        }
    }

    static String getCookie(String cookieName) {
        if (msCookieManager.getCookieStore().getCookies().size() > 0) {
            //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
            CookieStore cookieStore = msCookieManager.getCookieStore();
            for (HttpCookie cookie : cookieStore.getCookies()) {
                if (cookie.getName().equals(cookieName)) return cookie.getValue();
            }
        }
        return null;
    }

    private static void addCookiesToConnection(HttpURLConnection urlConnection) {
        if(msCookieManager.getCookieStore().getCookies().size() > 0)
        {
            //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
            urlConnection.setRequestProperty("Cookie",
                    TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
        }

    }
    public static String httpGet(URL url, int saveCookies) throws LoginError {
        try {
            HttpsURLConnection urlConnection;
            setTrustManager();
            BufferedReader reader;
            String jsonStr;
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            addCookiesToConnection(urlConnection);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 400) {
                throw new LoginError();
            }
            if (urlConnection.getResponseCode() == 401) {
                //throw new NoSessionError();
                throw new LoginError();
            }
            if (saveCookies != 0) {
                setCookies(urlConnection);
            }
            printCookies("httpGet: " + url.toString());
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

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String httpGet(URL url) throws LoginError {
        return httpGet(url, 0);
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

    private static void addJsonParamsToPost(HttpURLConnection urlConnection, JSONObject jsonObject) throws IOException {
        JSONObject jsonParams = jsonObject;
        try {
            jsonParams.put("csrfmiddlewaretoken", getCookie("csrftoken"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            printCookies("httpPost");
            addCookiesToConnection(urlConnection);
            addJsonParamsToPost(urlConnection, jsonParams);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 400) {
                throw new LoginError();
            }

            if (saveCookies != 0) {
                setCookies(urlConnection);
                printCookies("httpPost despues de setCookies");
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

        } catch (IOException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
}
