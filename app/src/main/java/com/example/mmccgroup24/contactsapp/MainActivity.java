package com.example.mmccgroup24.contactsapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mmccgroup24.contactsapp.models.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends ActionBarActivity {

    private static final int CREATE_CONTACT_REQUEST = 1;

    public Synchronization sync = new Synchronization();
    ContactListAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create JSONObject (TODO--> Add here the code to fetch from the server instead of this!!!!)

        if (Synchronization.isInternetAvailable(MainActivity.this)) {
            Toast.makeText(getBaseContext(), "Connected!", Toast.LENGTH_LONG).show();
        }
        // Create the adapter to convert the array to views
        adapter = new ContactListAdapter(this, User.users);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.contactList);
        listView.setAdapter(adapter);

        //Set listener for tap over item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = ((ContactListAdapter) parent.getAdapter()).getItem(position);
                Intent intent = new Intent(parent.getContext(), ContactInfoActivity.class);
                //Here maybe we could send it en JSON....
                intent.putExtra("contact", user);
                startActivity(intent);
            }
        });
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
        if (id == R.id.action_add_local) {
            Intent intent = new Intent(this, SearchLocalUserActivity.class);
            startActivityForResult(intent, CREATE_CONTACT_REQUEST);
            return true;
        }

        if (id == R.id.action_create_contact) {
            Intent intent = new Intent(this, CreateContactActivity.class);
            startActivityForResult(intent, CREATE_CONTACT_REQUEST);
        }
        if (id == R.id.action_sync_online) try {

            new LoginAsyncTask().execute("http://mccgroup24.ddns.net:8080//users/sign_in");
            new HttpAsyncTask().execute("http://mccgroup24.ddns.net:8080/contacts");

        } catch (Exception e) {
            Log.e("error", "" + e.getMessage());
            //return true;
        }
       /* else if (id = R.id.add_user){

            try {

                new newUserAsyncTask().execute("http://mccgroup24.ddns.net:8080/contacts/new");

            } catch (Exception e) {
                Log.e("error", "" + e.getMessage());
                //return true;
            }
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case CREATE_CONTACT_REQUEST:
                if (resultCode == RESULT_OK) {
                    User newUser = data.getExtras().getParcelable("new_contact");
                    User.users.add(newUser);
                    Collections.sort(User.users);
                    ((ListView) findViewById(R.id.contactList)).invalidateViews();
                }
                break;
        }
    }

    private class ContactListAdapter extends ArrayAdapter<User> {

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

        // View lookup cache
        private class ViewHolder {
            TextView name;
            TextView surname;
        }
    }

   /* private class newUserAysncTask extends  AsyncTaskString<String, Void, String >{


        @Override
        protected String doInBackground(String... urls) {

            return Synchronization.newUser(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();

        }


    }*/

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        JSONArray temp;

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... urls) {

            try {
                temp = Synchronization.GETUsers(urls[0]);

            } catch
                    (Exception e) {
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
