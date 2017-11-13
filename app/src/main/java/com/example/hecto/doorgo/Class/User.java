package com.example.hecto.doorgo.Class;

/**
 * Created by hecto on 26/09/2017.
 */

public class User {

    private Id _id;
    private String user;
    private String pass;
    private String imageProfile;


    public Id get_id() {
        return _id;
    }

    public String getUsername() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }
}
