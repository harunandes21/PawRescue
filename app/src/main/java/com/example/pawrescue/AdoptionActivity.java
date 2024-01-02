package com.example.pawrescue;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdoptionActivity extends AppCompatActivity {
    private Spinner locationSpinner,speciesSpinner,ageSpinner,healthStatusSpinner;
    private String location,species,age,healthStatus;
    private ArrayAdapter<CharSequence> adapterLocation,adapterSpecies,adapterAge,adapterHealth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption);

        // Connect UI elements
        //RecyclerView profileRecyclerView = findViewById(R.id.profileRecyclerView);
        locationSpinner = findViewById(R.id.locationSpinner);
        speciesSpinner = findViewById(R.id.speciesSpinner);
        ageSpinner = findViewById(R.id.ageSpinner);
        healthStatusSpinner = findViewById(R.id.healthStatusSpinner);
        Button adoptButton = findViewById(R.id.adoptButton);
        adapterLocation = ArrayAdapter.createFromResource(this,R.array.city_array, android.R.layout.simple_spinner_item);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapterLocation);
        adapterSpecies = ArrayAdapter.createFromResource(this,R.array.species_array, android.R.layout.simple_spinner_item);
        adapterSpecies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speciesSpinner.setAdapter(adapterSpecies);
        adapterAge = ArrayAdapter.createFromResource(this,R.array.age_array, android.R.layout.simple_spinner_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapterAge);
        adapterHealth = ArrayAdapter.createFromResource(this,R.array.health_array, android.R.layout.simple_spinner_item);
        adapterHealth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        healthStatusSpinner.setAdapter(adapterHealth);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        speciesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                species = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        healthStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                healthStatus = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adoptButton.setOnClickListener(view -> {
            adoptButton.setText(healthStatus);
        });
    }
}
