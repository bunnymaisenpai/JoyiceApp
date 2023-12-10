package com.example.myapplication.SignIn;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("_id")
    private String id;

    @SerializedName("fname")
    private String firstname;

    @SerializedName("lname")
    private String lastname;

    @SerializedName("address")
    private String address;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("cnumber")
    private String contactNumber;

    @SerializedName("isValid")
    private boolean isValid;

    public String getId() {
        return id;
    }

    public String getFirstname() {return firstname;}

    public String getLastname() {return lastname;}

    public String getAddress() {return address;}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public boolean isValid() {
        return isValid;
    }
}
