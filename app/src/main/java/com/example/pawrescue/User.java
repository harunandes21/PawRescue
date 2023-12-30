package com.example.pawrescue;

public class User {
    public long id;
    public String username;
    public String password;
    public String city;
    public String neighborhood;


    public User(long id, String username, String password, String city, String neighborhood) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.city = city;
        this.neighborhood = neighborhood;
    }


}
