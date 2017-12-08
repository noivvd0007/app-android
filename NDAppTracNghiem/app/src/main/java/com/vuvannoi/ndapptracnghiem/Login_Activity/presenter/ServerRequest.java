package com.vuvannoi.ndapptracnghiem.Login_Activity.presenter;

/**
 * Created by TiepNguyen on 26-07-17.
 */

public class ServerRequest {
    private String operation;
    private User user;
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public void setUser(User user) {
        this.user = user;
    }
}


