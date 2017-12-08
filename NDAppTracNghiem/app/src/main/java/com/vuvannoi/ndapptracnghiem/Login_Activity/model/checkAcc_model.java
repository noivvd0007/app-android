package com.vuvannoi.ndapptracnghiem.Login_Activity.model;

/**
 * Created by Admin on 9/6/2017.
 */

public class checkAcc_model {
    String id;
    String username;
    String pass;

    public checkAcc_model() {
    }

    public checkAcc_model(String id, String username, String pass) {
        this.id = id;
        this.username = username;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return id+"-"+username+"-"+pass;
    }
}
