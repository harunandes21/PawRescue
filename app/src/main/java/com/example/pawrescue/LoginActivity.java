package com.example.pawrescue;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pawrescue.data.NotDefterimContract;

public class LoginActivity extends AppCompatActivity {
    static final Uri CONTENT_URI = UserProvider.CONTENT_URI;
    private EditText editTextUsername, editTextPassword;
    private String username, password;
    private Button buttonLogin;
    private TextView textViewSignUp;
    private final DatabaseFunctions dbFunctions = new DatabaseFunctions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // Set click listener for the login button (you can implement your login logic here)
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send username and password to check
                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();
                getUserById(2);
                if (username.equals("") || password.equals("")) {
                    showAlertDialog("Error", "Username or password cannot be empty!");
                } else {
                    Boolean loginResult = dbFunctions.CheckUserName(username, password);
                    if (loginResult) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
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

    public User getUserById(long userId) {
        User user = null;
        String[] columns = {
                NotDefterimContract.UserEntry.COLUMN_USERNAME,
                NotDefterimContract.UserEntry.COLUMN_PASSWORD,
                NotDefterimContract.UserEntry.COLUMN_CITY,
                NotDefterimContract.UserEntry.COLUMN_NEIGHBORHOOD
        };

        String selection = NotDefterimContract.UserEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = getContentResolver().query(CONTENT_URI,columns,selection,selectionArgs,null);


        if (cursor.getColumnCount()>0 && cursor.moveToFirst()) {
            String username = cursor.getString(0);
            String password = cursor.getString(1);
            String city = cursor.getString(2);
            String neighborhood = cursor.getString(3);

            user = new User(userId, username, password, city, neighborhood);
            editTextUsername.setText(user.username);
            editTextPassword.setText(user.password);
            cursor.close();
        }

        return user;
    }
}
