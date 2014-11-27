package com.example.mmccgroup24.contactsapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity  {



    public Synchronization sync = new Synchronization();
    ContactListAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create JSONObject (TODO--> Add here the code to fetch from the server instead of this!!!!)

      //  if(sync.isConnected())

        if(Synchronization.isInternetAvailable(MainActivity.this)) {
            Toast.makeText(getBaseContext(), "Connected!", Toast.LENGTH_LONG).show();
        }
        String input = getString(R.string.JSONTest);
        JSONArray jObject = null;
        try {
             jObject= new JSONArray(input);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Construct the data source from a JSONArray
        User.users = User.fromJson(jObject);
        // Create the adapter to convert the array to views
        adapter = new ContactListAdapter(this, User.users);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.contactList);
        listView.setAdapter(adapter);

        //Set listener for tap over item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = ((ContactListAdapter)parent.getAdapter()).getItem(position);
                Intent intent = new Intent(parent.getContext(), ContactInfoActivity.class);
                //Here maybe we could send it en JSON....
                intent.putExtra("USER_NAME", user.name);
                intent.putExtra("USER_SURNAME", user.surname);
                intent.putExtra("USER_ADDRESS", user.address);
                intent.putExtra("USER_EMAIL", user.email);
                intent.putExtra("USER_PHONE", user.phone);
                intent.putExtra("USER_BIRTH", user.birthday);
                intent.putExtra("USER_NOTES", user.notes);
                startActivity(intent);
            }
        });
    }

    private class ContactListAdapter extends ArrayAdapter<User>{

        // View lookup cache
        private class ViewHolder {
            TextView name;
            TextView surname;
        }

        public ContactListAdapter(Context context, ArrayList<User> users) {
            super(context, R.layout.item_user, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            User user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_user, parent, false);
                viewHolder.name = (TextView) convertView.findViewById(R.id.item_user_name);
                viewHolder.surname = (TextView) convertView.findViewById(R.id.item_user_surname);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // Populate the data into the template view using the data object
            viewHolder.name.setText(user.name);
            viewHolder.surname.setText(user.surname);
            // Return the completed view to render on screen
            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }












    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sync_online) {


            try {

                new LoginAsyncTask().execute("http://mccgroup24.ddns.net:8080//users/sign_in");

                new HttpAsyncTask().execute("http://mccgroup24.ddns.net:8080/contacts");



            } catch (Exception e) {
                Log.e("error", "" + e.getMessage());
                //return true;
            }

            //
        }
        return super.onOptionsItemSelected(item);
    }







    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        JSONArray temp;

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... urls) {

            try {
                temp = Synchronization.GETUsers(urls[0]);

            } catch
                    (Exception e){
                Log.e("app", e.getMessage());
            }


            return Synchronization.GETUsers(urls[0]).toString();
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_LONG).show();
            User.fromJson(temp);
            adapter.notifyDataSetChanged();
        }
    }


    private class LoginAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return Synchronization.POST(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();

        }
    }





}
