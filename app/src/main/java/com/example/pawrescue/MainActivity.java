package com.example.pawrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.pawrescue.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);



        super.onCreate(savedInstanceState);
        ImageView profilePicture;
        ImageView badge1=findViewById(R.id.badge1);
        ImageView badge2=findViewById(R.id.badge2);
        TextView tBadge1=findViewById(R.id.T_badge1);
        TextView tBadge2=findViewById(R.id.T_badge2);
        profilePicture = findViewById(R.id.profilePicture);
        // recieve the user as parameter from the login page
        Intent intent = getIntent();
        User loggedInUser = (User) intent.getSerializableExtra("user");
        if (loggedInUser != null) {

            TextView userInfoTextView = findViewById(R.id.username);
            String userInfo = loggedInUser.username;
            userInfoTextView.setText(userInfo);
            profilePicture.setImageResource(loggedInUser.getAvatarDrawableResource());
            TextView userPointView = findViewById(R.id.point);
            int avatarIndex = loggedInUser.avatarIndex;

            String userPoint = String.valueOf(loggedInUser.point);
            userPointView.setText(userPoint);


           if(userPoint.equals("100"))
            {
                badge1.setVisibility(View.VISIBLE);
                tBadge1.setVisibility(View.VISIBLE);
                badge2.setVisibility(View.INVISIBLE);

                tBadge2.setVisibility(View.INVISIBLE);

            }
            else if(userPoint.equals("110"))
           {
               badge1.setVisibility(View.VISIBLE);
               tBadge1.setVisibility(View.VISIBLE);
               badge2.setVisibility(View.VISIBLE);

               tBadge2.setVisibility(View.VISIBLE);

           }
            else {
               badge1.setVisibility(View.INVISIBLE);
               tBadge1.setVisibility(View.INVISIBLE);
               badge2.setVisibility(View.INVISIBLE);

               tBadge2.setVisibility(View.INVISIBLE);

           }






        }
    }


}