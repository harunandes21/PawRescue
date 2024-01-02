package com.example.pawrescue;

import java.io.Serializable;

public class User implements Serializable {
    public long id;
    public String username;
    public String password;
    public String city;


    public User(long id, String username, String password, String city) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.city = city;
    }


}
