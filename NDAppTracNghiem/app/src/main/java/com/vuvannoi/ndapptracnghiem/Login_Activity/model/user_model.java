package com.vuvannoi.ndapptracnghiem.Login_Activity.model;

/**
 * Created by Admin on 9/6/2017.
 */
public class user_model {
    String id;
    String user;
    String email;
    String phone;
    String pass;

    public user_model() {
    }

    public user_model(String id, String user, String email, String phone, String pass) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.phone = phone;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return user+"-"+pass;
    }
}
