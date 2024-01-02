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
        Avatar avatar3 = new Avatar();
        avatar3.setName("Avatar 3");
        avatar3.setImage(R.drawable.avatar3);
        avatarList.add(avatar3);
        Avatar avatar4 = new Avatar();
        avatar4.setName("Avatar 4");
        avatar4.setImage(R.drawable.avatar4);
        avatarList.add(avatar4);

        return avatarList;
    }
}
