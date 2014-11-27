package com.example.mmccgroup24.contactsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Erik on 25/11/2014.
 */
public class User implements Parcelable, Comparable<User> {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    public static ArrayList<User> users = new ArrayList<>();
    public String name, surname, address, email, phone, birthday, notes;

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

    public User(Parcel in) {
        String[] data = new String[7];

        in.readStringArray(data);
        this.name = data[0];
        this.surname = data[1];
        this.address = data[2];
        this.birthday = data[3];
        this.phone = data[4];
        this.email = data[5];
        this.notes = data[6];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeStringArray(new String[]{this.name, this.surname, this.address, this.birthday, this.phone, this.email, this.notes});

    }

    @Override
    public int compareTo(User another) {
        return this.name.compareTo(another.name);
    }

}
