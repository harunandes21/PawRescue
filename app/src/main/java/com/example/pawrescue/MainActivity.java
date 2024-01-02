package com.example.pawrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public ImageView profilePicture;
    private GestureDetector gestureDetector;
    private boolean isZoomed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView badge1=findViewById(R.id.badge1);
        ImageView badge2=findViewById(R.id.badge2);
        TextView tBadge1=findViewById(R.id.T_badge1);
        TextView tBadge2=findViewById(R.id.T_badge2);
        profilePicture = findViewById(R.id.profilePicture);
        badge1.setOnTouchListener(new OnDoubleTapListener(this, badge1));
        badge2.setOnTouchListener(new OnDoubleTapListener(this, badge2));

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
            int userPointInt = Integer.parseInt(userPoint);
            userPointView.setText(userPoint);

            profilePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click action here
                    Toast.makeText(MainActivity.this, "Image Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            profilePicture.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Handle long press action here
                    Toast.makeText(MainActivity.this, "Image Long Pressed", Toast.LENGTH_SHORT).show();
                    // Enlarge the image on long press
                    performZoomIn();
                    return true; // Return true to consume the long click event
                }
            });

            profilePicture.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Reset the image size on touch release
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        performZoomOut();
                    }
                    return false;
                }
            });

//           if(userPointInt < 100)
//            {
//                badge1.setVisibility(View.INVISIBLE);
//                tBadge1.setVisibility(View.INVISIBLE);
//                badge2.setVisibility(View.INVISIBLE);
//                tBadge2.setVisibility(View.INVISIBLE);
//
//            }
//            else if(userPointInt >= 100 && userPointInt < 110)
//           {
//               badge1.setVisibility(View.VISIBLE);
//               tBadge1.setVisibility(View.VISIBLE);
//               badge2.setVisibility(View.INVISIBLE);
//               tBadge2.setVisibility(View.INVISIBLE);
//
//           }
//            else if (userPointInt >= 110) {
//               badge1.setVisibility(View.VISIBLE);
//               tBadge1.setVisibility(View.VISIBLE);
//               badge2.setVisibility(View.VISIBLE);
//               tBadge2.setVisibility(View.VISIBLE);
//
//           }
        }
    }

    private void performZoomIn() {
        // Zoom in effect using ObjectAnimator
        Animator scaleUpX = ObjectAnimator.ofFloat(profilePicture, "scaleX", 1.0f, 1.5f);
        Animator scaleUpY = ObjectAnimator.ofFloat(profilePicture, "scaleY", 1.0f, 1.5f);
        scaleUpX.setDuration(300);
        scaleUpY.setDuration(300);
        scaleUpX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleUpY.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleUpX.start();
        scaleUpY.start();
    }

    private void performZoomOut() {
        // Zoom out effect using ObjectAnimator
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(profilePicture, "scaleX", 1.5f, 1.0f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(profilePicture, "scaleY", 1.5f, 1.0f);
        scaleDownX.setDuration(300);
        scaleDownY.setDuration(300);
        scaleDownX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleDownY.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleDownX.start();
        scaleDownY.start();
    }

    private class OnDoubleTapListener implements View.OnTouchListener {
        private GestureDetector gestureDetector;
        private ImageView imageView;

        public OnDoubleTapListener(Context context, ImageView imageView) {
            this.gestureDetector = new GestureDetector(context, new GestureListener());
            this.imageView = imageView;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureDetector.onTouchEvent(event);
            return true;
        }

        private class GestureListener extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isZoomed) {
                    performZoomOutbadge(imageView);
                } else {
                    performZoomInbadge(imageView);
                }
                isZoomed = !isZoomed;
                return true;
            }
        }
    }

    private void performZoomInbadge(ImageView imageView) {
        // Zoom in effect using ObjectAnimator
        Animator scaleUpX = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 1.5f);
        Animator scaleUpY = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 1.5f);
        scaleUpX.setDuration(300);
        scaleUpY.setDuration(300);
        scaleUpX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleUpY.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleUpX.start();
        scaleUpY.start();
    }

    private void performZoomOutbadge(ImageView imageView) {
        // Zoom out effect using ObjectAnimator
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(imageView, "scaleX", 1.5f, 1.0f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(imageView, "scaleY", 1.5f, 1.0f);
        scaleDownX.setDuration(300);
        scaleDownY.setDuration(300);
        scaleDownX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleDownY.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleDownX.start();
        scaleDownY.start();
    }
}