package com.example.pawrescue;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pawrescue.data.ProviderContract;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().setNavigationBarColor(getResources().getColor(android.R.color.transparent, getTheme()));
            }
        }
        setContentView(R.layout.activity_login);

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        // Set click listener for the "Sign Up" text
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to SignUpActivity
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for the login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send username and password to check
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (username.equals("") || password.equals("")) {
                    showAlertDialog("Error", "Username or password cannot be empty!");
                } else {
                    User loggedInUser = loginUser(username, password);
                    if (loggedInUser != null) {
                        // Successfully logged in, you can do further actions
                        // For example, redirect to the main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("user", loggedInUser);
                        startActivity(intent);
                        finish();
                    } else {
                        showAlertDialog("Error", "Invalid username or password!");
                    }
                }
            }
        });
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private User loginUser(String username, String password) {
        String[] columns = {
                ProviderContract.UserEntry._ID,
                ProviderContract.UserEntry.COLUMN_CITY,
                ProviderContract.UserEntry.COLUMN_AVATAR_INDEX,
                ProviderContract.UserEntry.COLUMN_POINT,


        };

        String selection = ProviderContract.UserEntry.COLUMN_USERNAME + " = ? AND " +
                ProviderContract.UserEntry.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = getContentResolver().query(
                UserProvider.CONTENT_URI_USER,
                columns,
                selection,
                selectionArgs,
                null
        );

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            long userId = cursor.getLong(0);
            String city = cursor.getString(1);
            int avatarIndex=cursor.getInt(2);
            int point=cursor.getInt(3);


            user = new User(userId, username, password,point, city, avatarIndex);
            cursor.close();
        }

        return user;
    }
}
