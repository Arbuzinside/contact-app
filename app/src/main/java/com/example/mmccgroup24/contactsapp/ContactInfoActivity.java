package com.example.mmccgroup24.contactsapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mmccgroup24.contactsapp.models.User;


public class ContactInfoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        User contact = extras.getParcelable("contact");

        setContentView(R.layout.activity_contact_info);
        ((TextView) findViewById(R.id.user_name)).setText(contact.name);
        ((TextView) findViewById(R.id.user_surname)).setText(contact.surname);
        ((TextView) findViewById(R.id.user_address)).setText(contact.address);
        ((TextView) findViewById(R.id.user_mail)).setText(contact.email);
        ((TextView) findViewById(R.id.user_phone)).setText(contact.phone);
        ((TextView) findViewById(R.id.user_birth)).setText(contact.birthday);
        ((TextView) findViewById(R.id.user_birth)).setText(contact.notes);
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
        if (id == R.id.action_save_local) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean syncGlobally(){


        return true;
    }
}
