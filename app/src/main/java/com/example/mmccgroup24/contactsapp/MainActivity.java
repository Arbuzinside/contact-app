package com.example.mmccgroup24.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create JSONObject (TODO--> Add here the code to fetch from the server instead of this!!!!)
        String input = getString(R.string.JSONTest);
        JSONArray jObject = null;
        try {
             jObject= new JSONArray(input);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Construct the data source from a JSONArray
        ArrayList<User> arrayOfUsers = User.fromJson(jObject);
        // Create the adapter to convert the array to views
        ContactListAdapter adapter = new ContactListAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.contactList);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
