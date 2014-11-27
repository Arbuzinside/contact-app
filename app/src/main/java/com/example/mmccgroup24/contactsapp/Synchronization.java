package com.example.mmccgroup24.contactsapp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

/**
 * Created by dima on 26.11.2014.
 */




public class Synchronization extends ActionBarActivity {




    static final String TAG = Synchronization.class.getSimpleName();
    public static CookieStore cookieStore = new BasicCookieStore();
    public static HttpClient httpclient = new DefaultHttpClient();



 /*   public static String Delete(String url, JSONObject user)
    {
        String responseString = null;
        try {
            // Add your data

            HttpDelete delete = new HttpDelete();
            StringEntity se = new StringEntity(user.toString());
            delete.setURI(URI.create(url +"/" + id));
            delete.setHeader("Accept", "application/json");
            delete.setHeader("Content-type", "application/json");
            HttpContext localContext = new BasicHttpContext();
            // Bind custom cookie store to the local context
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);





            HttpResponse response = httpclient.execute(delete, localContext);
            HttpEntity entity = response.getEntity();
            responseString = EntityUtils.toString(entity, "UTF-8");



        } catch (ClientProtocolException e) {
            Log.e("error", "" + e.getMessage());
        } catch (IOException e) {
            Log.e("error", "" + e.getMessage());
        }

        return responseString;



    }*/


    public static String POST(String url){

        String responseString = null;
        try {
            // Add your data



            HttpPost httppost = new HttpPost();
            StringEntity se = new StringEntity("{\"user\":{\"email\":\"test@test.com\",\"password\":\"password\"}}",  "UTF-8");
            httppost.setURI(URI.create(url));
            httppost.setEntity(se);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");

            HttpContext localContext = new BasicHttpContext();
            // Bind custom cookie store to the local context
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);





            HttpResponse response = httpclient.execute(httppost, localContext );
            HttpEntity entity = response.getEntity();
            responseString = EntityUtils.toString(entity, "UTF-8");



        } catch (ClientProtocolException e) {
            Log.e("error", "" + e.getMessage());
        } catch (IOException e) {
            Log.e("error", "" + e.getMessage());
        }

        return responseString;

    }


    public static JSONArray GETUsers(String url){
        InputStream inputStream = null;
        JSONArray result = null;


        try {

            // create HttpClient

            HttpGet request = new HttpGet(url);
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(request,localContext);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null) {
                result = convertInputStreamToJSONArray(inputStream);

            }
            else
                return null;

        } catch (Exception e) {
             Log.e("app", e.getLocalizedMessage());
        }

        return result;
    }

    public static String newUser(String url, JSONObject user)
    {

        String responseString = null;
        try {
            // Add your data

            HttpPost httppost = new HttpPost();
            StringEntity se = new StringEntity(user.toString());
            httppost.setURI(URI.create(url));
            httppost.setEntity(se);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");

            HttpContext localContext = new BasicHttpContext();
            // Bind custom cookie store to the local context
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);





            HttpResponse response = httpclient.execute(httppost, localContext);
            HttpEntity entity = response.getEntity();
            responseString = EntityUtils.toString(entity, "UTF-8");



        } catch (ClientProtocolException e) {
            Log.e("error", "" + e.getMessage());
        } catch (IOException e) {
            Log.e("error", "" + e.getMessage());
        }

        return responseString;





    }











    public static boolean isInternetAvailable(Context context)
    {


        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null)
        {
            Log.d(TAG,"no internet connection");
            return false;
        }
        else
        {
            if(info.isConnected())
            {
                Log.d(TAG," internet connection available...");
                return true;
            }
            else
            {
                Log.d(TAG," internet connection");
                return true;
            }

        }
    }



    public boolean isConnected(){

        try {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
                return true;
            else
                return false;
        }
        catch(Throwable e)
        {
            Log.e("app", "exception", e);
            return false;
        }
    }



    private static JSONArray convertInputStreamToJSONArray(InputStream inputStream) throws IOException
    {

        JSONArray users = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";

        try {

            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();

            users = new JSONArray(result);
        }
        catch (Exception e)
        {
            Log.e("app", e.getMessage());
        }
        return users;
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


}
