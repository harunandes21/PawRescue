package com.example.pawrescue;

import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pawrescue.data.NotDefterimContract;

public class SignupActivity extends AppCompatActivity {

    static final Uri CONTENT_URI = UserProvider.CONTENT_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Get references to the Spinners
        Spinner spinnerCity = findViewById(R.id.spinnerCity);
        Spinner spinnerNeighborhood = findViewById(R.id.spinnerNeighborhood);

        // Create ArrayAdapter using the string arrays and default spinner layout
        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.city_array,
                android.R.layout.simple_spinner_item
        );

        ArrayAdapter<CharSequence> neighborhoodAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.neighborhood_array,
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list appears
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        neighborhoodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerCity.setAdapter(cityAdapter);
        spinnerNeighborhood.setAdapter(neighborhoodAdapter);

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
                Spinner neighborhoodSpinner = findViewById(R.id.spinnerNeighborhood);

                // Get the text entered by the user
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String city = citySpinner.getSelectedItem().toString();
                String neighborhood = neighborhoodSpinner.getSelectedItem().toString();

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

                // Insert data into content provider
                ContentValues values = new ContentValues();
                values.put(NotDefterimContract.UserEntry.COLUMN_USERNAME, username);
                values.put(NotDefterimContract.UserEntry.COLUMN_PASSWORD, password);
                values.put(NotDefterimContract.UserEntry.COLUMN_CITY, city);
                values.put(NotDefterimContract.UserEntry.COLUMN_NEIGHBORHOOD, neighborhood);

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
                Toast.makeText(SignupActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                // Redirect to login page (you need to implement the logic for redirection)
            } else {
                // Handle errors or show a message for unsuccessful insertion
                Toast.makeText(SignupActivity.this, result, Toast.LENGTH_SHORT).show();
            }

        }

    }
}
