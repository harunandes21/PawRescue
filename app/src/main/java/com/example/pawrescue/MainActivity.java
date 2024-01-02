package com.example.pawrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.pawrescue.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView profilePicture;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profilePicture = findViewById(R.id.profilePicture);
        // recieve the user as parameter from the login page
        Intent intent = getIntent();
        User loggedInUser = (User) intent.getSerializableExtra("user");
        if (loggedInUser != null) {
            // Example: Display user information in a TextView
            TextView userInfoTextView = findViewById(R.id.username);
            String userInfo = loggedInUser.username;
            userInfoTextView.setText(userInfo);
            profilePicture.setImageResource(loggedInUser.getAvatarDrawableResource());
            TextView userPointView = findViewById(R.id.point);
            int avatarIndex = loggedInUser.avatarIndex;
            String userPoint = String.valueOf(loggedInUser.point);
            userPointView.setText(userPoint);

        }
    }
}