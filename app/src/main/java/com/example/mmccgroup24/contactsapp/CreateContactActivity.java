package com.example.mmccgroup24.contactsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mmccgroup24.contactsapp.models.User;


public class CreateContactActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        User contact = null;
        try {
            contact = getIntent().getExtras().getParcelable("contact");
            if (contact != null) {
                ((TextView) findViewById(R.id.user_name)).setText(contact.name);
                ((TextView) findViewById(R.id.user_surname)).setText(contact.surname);
                ((TextView) findViewById(R.id.user_address)).setText(contact.address);
                ((TextView) findViewById(R.id.user_email)).setText(contact.email);
                ((TextView) findViewById(R.id.user_phone)).setText(contact.phone);
                ((TextView) findViewById(R.id.user_birth)).setText(contact.birthday);
                ((TextView) findViewById(R.id.user_birth)).setText(contact.notes);
            }
        } catch (NullPointerException ignored) {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_accept) {
            String name = ((EditText) findViewById(R.id.user_name)).getText().toString();
            String surname = ((EditText) findViewById(R.id.user_surname)).getText().toString();
            String address = ((EditText) findViewById(R.id.user_address)).getText().toString();
            String mail = ((EditText) findViewById(R.id.user_email)).getText().toString();
            String phone = ((EditText) findViewById(R.id.user_phone)).getText().toString();
            String birth = ((EditText) findViewById(R.id.user_birth)).getText().toString();
            String notes = ((EditText) findViewById(R.id.user_notes)).getText().toString();
            User res = new User(name, surname, address, mail, phone, birth, notes);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("new_contact", res);
            setResult(RESULT_OK, resultIntent);
            finish();
            return true;
        }

        if (id == R.id.action_cancel) {
            Intent resultIntent = new Intent();
            setResult(RESULT_CANCELED, resultIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

