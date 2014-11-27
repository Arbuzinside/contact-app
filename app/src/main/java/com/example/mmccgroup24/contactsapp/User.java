package com.example.mmccgroup24.contactsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Erik on 25/11/2014.
 */
public class User {
    public String name, surname, address, email, phone, birthday, notes;
    public static ArrayList<User> users = new ArrayList<>();

    // Constructor to convert JSON object into a Java class instance
    public User(JSONObject object) {
        try {
            this.name = object.getString("name");
            this.surname = object.getString("surname");
            this.address = object.getString("address");
            this.email = object.getString("email");
            this.phone = object.getString("phone");
            this.birthday = object.getString("birthday");
            this.notes = object.getString("notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User(String name, String surname, String address, String email, String phone, String birthday, String notes) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.notes = notes;
    }

    // Factory method to convert an array of JSON objects into a list of objects
    // User.fromJson(jsonArray);
    public static ArrayList<User> fromJson(JSONArray jsonObjects) {
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                users.add(new User(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

}
