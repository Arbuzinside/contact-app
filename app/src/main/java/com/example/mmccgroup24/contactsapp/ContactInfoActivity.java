package com.example.mmccgroup24.contactsapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mmccgroup24.contactsapp.models.User;


public class ContactInfoActivity extends ActionBarActivity {

    private User contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        contact = extras.getParcelable("contact");

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
            // Creates a new Intent to insert a contact
            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            // Sets the MIME type to match the Contacts Provider
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            // Inserts name of the contact
            intent.putExtra(ContactsContract.Intents.Insert.NAME, contact.name);
            // Inserts address
            intent.putExtra(ContactsContract.Intents.Insert.POSTAL, contact.address);
            // Inserts an email address as work's mail address
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL,contact.email);
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
            // Inserts phone number as work's phone number
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, contact.phone);
            intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
            // Send the intent
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean syncGlobally(){


        return true;
    }
}
