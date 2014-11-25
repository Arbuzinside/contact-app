package com.example.mmccgroup24.contactsapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ContactInfoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("USER_NAME");
        String surname = extras.getString("USER_SURNAME");
        String address = extras.getString("USER_ADDRESS");
        String email = extras.getString("USER_EMAIL");
        String phone = extras.getString("USER_PHONE");
        String birthday = extras.getString("USER_BIRTH");
        String notes = extras.getString("USER_NOTES");

        setContentView(R.layout.activity_contact_info);
        ((TextView) findViewById(R.id.user_name)).setText(name);
        ((TextView) findViewById(R.id.user_surname)).setText(surname);
        ((TextView) findViewById(R.id.user_address)).setText(address);
        ((TextView) findViewById(R.id.user_mail)).setText(email);
        ((TextView) findViewById(R.id.user_phone)).setText(phone);
        ((TextView) findViewById(R.id.user_birth)).setText(birthday);
        ((TextView) findViewById(R.id.user_notes)).setText(notes);
        //Then render the layout

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_info, menu);
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
