package com.example.pawrescue;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

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
    }
}
