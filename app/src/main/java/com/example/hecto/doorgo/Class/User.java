package com.example.hecto.doorgo.Class;

/**
 * Created by hecto on 26/09/2017.
 */

public class User {

    private Id _id;
    private String user;
    private String pass;

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public String getUsername() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
