package com.example.pawrescue;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pawrescue.data.NotDefterimContract;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    static final Uri CONTENT_URI = UserProvider.CONTENT_URI_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Get references to the Spinners
        Spinner spinnerCity = findViewById(R.id.spinnerCity);
        Spinner spinnerAvatar = findViewById(R.id.avatarSpinner);

        // Create ArrayAdapter using the string arrays and default spinner layout
        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.city_array,
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list appears
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AvatarAdapter adapter = new AvatarAdapter(SignupActivity.this, AvatarData.getAvatarList());

        // Apply the adapter to the spinner
        spinnerCity.setAdapter(cityAdapter);
        spinnerAvatar.setAdapter(adapter);

        Button signupButton = findViewById(R.id.buttonSignup);
        signupButton.setOnClickListener(view -> new InsertDataTask().execute());

    }

    private class InsertDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Get references to the EditText fields
                EditText usernameEditText = findViewById(R.id.editTextUsernameSignup);
                EditText passwordEditText = findViewById(R.id.editTextPasswordSignup);
                EditText confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
                Spinner citySpinner = findViewById(R.id.spinnerCity);
                Spinner spinnerAvatar = findViewById(R.id.avatarSpinner);

                // Get the text entered by the user
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String city = citySpinner.getSelectedItem().toString();
                String avatar = spinnerAvatar.getSelectedItem().toString();
                //Log.wtf("BatuhanDebug", avatar);

                // Check if any field is empty

                if (username.isEmpty()&&password.isEmpty()&&confirmPassword.isEmpty()) {
                    return "All fields must be filled ";
                }
                if (username.isEmpty()) {
                    return "User Name can not be empty";
                }


                if (password.isEmpty()) {
                    return "Password cannot be empty";
                }

                if (confirmPassword.isEmpty()) {
                    return "Confirm password cannot be empty";
                }

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    return "Passwords do not match";
                }
                Cursor usernameCursor = getContentResolver().query(
                        CONTENT_URI,
                        null,
                        NotDefterimContract.UserEntry.COLUMN_USERNAME + "=?",
                        new String[]{username},
                        null
                );

                if (usernameCursor != null) {
                    if (usernameCursor.getCount() > 0) {
                        // Username already exists
                        usernameCursor.close();
                        return "Username already exists";
                    }
                    usernameCursor.close();
                }

                // Insert data into content provider
                ContentValues values = new ContentValues();
                values.put(NotDefterimContract.UserEntry.COLUMN_USERNAME, username);
                values.put(NotDefterimContract.UserEntry.COLUMN_PASSWORD, password);
                values.put(NotDefterimContract.UserEntry.COLUMN_CITY, city);
                values.put(NotDefterimContract.UserEntry.COLUMN_AVATAR_INDEX, avatar);

                getContentResolver().insert(CONTENT_URI, values);

                return "Success"; // Successfully inserted data
            } catch (Exception e) {
                e.printStackTrace();
                return "Error during insertion: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("Success")) {
                // Add any additional handling or UI updates for successful insertion
                //Toast.makeText(SignupActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                showSuccessMessage();

                // Redirect to login page (you need to implement the logic for redirection)
            } else {
                // Handle errors or show a message for unsuccessful insertion
                Toast.makeText(SignupActivity.this, result, Toast.LENGTH_SHORT).show();
            }

        }

    }
    private void showSuccessMessage() {
        // Create an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You are registered successfully!")
                .setMessage("You are being redirected to login page. ");

        // Create the AlertDialog
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Automatically dismiss the AlertDialog after 2 seconds
        View view = Objects.requireNonNull(alertDialog.getWindow()).getDecorView();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Dismiss the AlertDialog after the desired duration
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                    finish();
                }
            }
        }, 2000); // Adjust the delay time as needed (in milliseconds)
    }
}
