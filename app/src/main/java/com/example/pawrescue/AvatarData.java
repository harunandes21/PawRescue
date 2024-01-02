package com.example.pawrescue;

import java.util.ArrayList;
import java.util.List;

public class AvatarData {

    public static List<Avatar> getAvatarList() {

        List<Avatar> avatarList = new ArrayList<>();

        Avatar avatar1 = new Avatar();
        avatar1.setName("Avatar 1");
        avatar1.setImage(R.drawable.avatar1);
        avatarList.add(avatar1);

        Avatar avatar2 = new Avatar();
        avatar2.setName("Avatar 2");
        avatar2.setImage(R.drawable.avatar2);
        avatarList.add(avatar2);

        return avatarList;
    }
}
