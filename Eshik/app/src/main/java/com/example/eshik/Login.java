package com.example.eshik;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login {
    private  String username,password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Login() {
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }





}
