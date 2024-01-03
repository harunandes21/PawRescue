package com.example.pawrescue;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pawrescue.data.NotDefterimContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdoptionActivity extends AppCompatActivity {
    static final Uri CONTENT_URI_USER = UserProvider.CONTENT_URI_USER;
    static final Uri CONTENT_URI_PET = UserProvider.CONTENT_URI_PET;
    static final Uri CONTENT_URI_ADOPTION = UserProvider.CONTENT_URI_ADOPTION;
    private Spinner locationSpinner,speciesSpinner,ageSpinner,healthStatusSpinner;
    private String location,species,age,healthStatus;
    private ArrayAdapter<CharSequence> adapterLocation,adapterSpecies,adapterAge, gender;
    public String jsonString;
    public String apiUrl_type;
    public String apiUrl_age;
    public String apiUrl_gender;
    public String apiUrl_location;
    public int animalIndex = 0;
    View searchButton;
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
        adapterLocation = ArrayAdapter.createFromResource(this,R.array.city_array_us, android.R.layout.simple_spinner_item);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapterLocation);
        adapterSpecies = ArrayAdapter.createFromResource(this,R.array.species_array, android.R.layout.simple_spinner_item);
        adapterSpecies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speciesSpinner.setAdapter(adapterSpecies);
        adapterAge = ArrayAdapter.createFromResource(this,R.array.age_array, android.R.layout.simple_spinner_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapterAge);
        gender = ArrayAdapter.createFromResource(this,R.array.gender, android.R.layout.simple_spinner_item);
        gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        healthStatusSpinner.setAdapter(gender);
        searchButton = findViewById(R.id.imageSearchButton);
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

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchDataFromApi().execute();
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

    // AsyncTask to perform network operation in the background
    private class FetchDataFromApi extends AsyncTask<Void, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Show a progress dialog or any UI indication
            progressDialog = ProgressDialog.show(AdoptionActivity.this, "Fetching Data From API", "Please wait...", true, false);
        }

        @Override
        protected String doInBackground(Void... params) {
            // Perform your network operation here
            // This method runs in a background thread
            try {
                return getJsonFromApi();
            } catch (IOException e) {
                e.printStackTrace();
                return "Error occurred during network operation";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Update UI with the result
            // This method runs on the main thread
            progressDialog.dismiss(); // Dismiss the progress dialog
            try {
                parseApiDataToScreen();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getJsonFromApi() throws IOException {

        apiUrl_location = locationSpinner.getSelectedItem().toString();
        apiUrl_type = speciesSpinner.getSelectedItem().toString();
        apiUrl_gender = healthStatusSpinner.getSelectedItem().toString();
        apiUrl_age = ageSpinner.getSelectedItem().toString();
        String apiUrl = "https://api.petfinder.com/v2/animals?type="+apiUrl_type+"&age="+apiUrl_age+
                "&gender="+apiUrl_gender+"&location="+apiUrl_location+"&limit=100&page=1";

        String authToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJZYjdHRkpvS0Q0bDQ0N1BkVFRYRjljdXdwakVPRzd0WG13MW9hOHlNVE1pQ2lRYnp1YyIsImp0aSI6IjQzYjg5NmUwMDUxNDVhNGJhNmZlYjcxYTNlZWIxYmExY2MzMDJhMmM0YjZjYmExMDIxMGFjNWM1ZDJjMzgzODY3NjJkNTBjOTdmNjU5NjRhIiwiaWF0IjoxNzA0MzAyNzA4LCJuYmYiOjE3MDQzMDI3MDgsImV4cCI6MTcwNDMwNjMwOCwic3ViIjoiIiwic2NvcGVzIjpbXX0.vaj1KNRUWQFccjScEVrq21GrQQqDNMGbn3SoPcjL5zqsbtTMDyupVLKkYB_V2UHg4H3Y_xiGChVcjp-iv-EQENNpou_Lr251wYdUidfCEaU_8C8cVbnGmvfrmwhLI-G5Lp9ivqM35MNfQu9CxoHdBSXum6YGmIA-q71hBOW7yFUKEK0xwgfJzctBoVjvYMp55XAv8ho1ZalHvD5uUOPujJ25Zns2kWwROyg7-AbvtTXRTXV0srta8zF9qZypJX3b1hp8RQ8-eYdW9paFnQcq6HNNZpTtgSoxTKvI4W466ukH3GpKx0s1alUF8DkJrkZintRzXqC-U-GEiVgZAcIkQQ";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to GET
        connection.setRequestMethod("GET");

        // Set the Authorization header
        connection.setRequestProperty("Authorization", authToken);

        // Get the response code
        int responseCode = connection.getResponseCode();

        // Read the response from the API
        BufferedReader reader;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        // Read the response from the API
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader2 = new BufferedReader(new InputStreamReader(
                responseCode == HttpURLConnection.HTTP_OK ? connection.getInputStream() : connection.getErrorStream()))) {
            String line;
            while ((line = reader2.readLine()) != null) {
                response.append(line);
            }
        }

        // Close the connection
        connection.disconnect();

        jsonString = response.toString();
        //Log.wtf("BatuhanDebug", jsonString);
        return response.toString();
    }

    public void parseApiDataToScreen() throws JSONException {

        JSONObject obj = new JSONObject(jsonString);
        JSONArray arr = obj.getJSONArray("animals");


        for (int i = 0; i < arr.length(); i++)
        {
            JSONArray photos = arr.getJSONObject(i).getJSONArray("photos");
            if (photos.length() == 0) continue;
            String photoLink = photos.getJSONObject(0).getString("full");
            Log.wtf("BatuhanDebug", photoLink);
        }



        //String petName = obj.getString("name");
        //String status = obj.getString("status");
        //String city = obj.getJSONObject("contact").getJSONObject("address").getString("city");

    }
}
