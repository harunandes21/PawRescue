package com.example.pawrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

//import com.example.pawrescue.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // recieve the user as parameter from the login page
        Intent intent = getIntent();
        User loggedInUser = (User) intent.getSerializableExtra("user");
        if (loggedInUser != null) {
            // Example: Display user information in a TextView
            TextView userInfoTextView = findViewById(R.id.username);
            String userInfo = loggedInUser.username;
            userInfoTextView.setText(userInfo);
            TextView userPointView = findViewById(R.id.point);
            String userPoint = loggedInUser.username;
            userPointView.setText(userInfo);

        }
    }
}