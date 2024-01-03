package com.example.pawrescue;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawrescue.data.NotDefterimContract;

public class AdoptionActivity extends AppCompatActivity {
    static final Uri CONTENT_URI_USER = UserProvider.CONTENT_URI_USER;
    static final Uri CONTENT_URI_PET = UserProvider.CONTENT_URI_PET;
    static final Uri CONTENT_URI_ADOPTION = UserProvider.CONTENT_URI_ADOPTION;
    private Spinner locationSpinner,speciesSpinner,ageSpinner,healthStatusSpinner;
    private String location,species,age,healthStatus;
    private ArrayAdapter<CharSequence> adapterLocation,adapterSpecies,adapterAge,adapterHealth;
    Intent intent;
    User loggedInUser;
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
        intent = getIntent();
        loggedInUser = (User) intent.getSerializableExtra("user");

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

            showAlertDialog("Success!", "You have successful adopted a pet and earned 100 points!!!");
        });
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    loggedInUser.point += 100;

                    ContentValues values = new ContentValues();
                    values.put(NotDefterimContract.UserEntry.COLUMN_POINT, loggedInUser.point);
                    String userId = String.valueOf(loggedInUser.id);
                    String selection = NotDefterimContract.UserEntry._ID + " = ? ";
                    String selectionArgs[] = {userId};
                    getContentResolver().update(CONTENT_URI_USER,values,selection,selectionArgs);

                    Intent mainIntent = new Intent(AdoptionActivity.this, MainActivity.class);
                    mainIntent.putExtra("user", loggedInUser);
                    startActivity(mainIntent);
                    finish();
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
