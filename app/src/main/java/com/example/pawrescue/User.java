package com.example.pawrescue;

import java.io.Serializable;


public class User implements Serializable {
    public long id;
    public String username;
    public String password;
    public int point;
    public String city;
    public int avatarIndex;
    public int adoptionID;


    public User(long id, String username, String password, int point,String city,int avatarIndex) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.city = city;
        this.point = point;
        this.avatarIndex = avatarIndex;
    }
    public User(long id, String username, String password, int point,String city,int avatarIndex,int adoptionID) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.city = city;
        this.point = point;
        this.avatarIndex = avatarIndex;
    }
    public int getAvatarDrawableResource() {
        switch (avatarIndex) {
            case 0:
                return R.drawable.avatar1;
            case 1:
                return R.drawable.avatar2;
            // Add more cases as needed
            default:
                return R.drawable.profile_icon; // Provide a default drawable if needed
        }

    }
}
