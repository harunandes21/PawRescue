package com.example.pawrescue;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmergencyActivity extends AppCompatActivity {
    private Spinner countrySpinner;
    private TextView text1,text2,text3;
    private ArrayAdapter<CharSequence> adapterCountry;
    private Button bt;
    private String selectedCountry;

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
        setContentView(R.layout.activity_emergency);


        bt = findViewById(R.id.button);
        text1 = findViewById(R.id.editText1);
        text2 = findViewById(R.id.editText2);
        text3 = findViewById(R.id.editText3);
        countrySpinner = findViewById(R.id.spinner);
        adapterCountry = ArrayAdapter.createFromResource(this,R.array.sos_array, android.R.layout.simple_spinner_item);
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapterCountry);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCall();
            }
        });

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    private void jsonParser(){
        
    }

    @SuppressLint("StaticFieldLeak")
    private void apiCall() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    selectedCountry = "TR";
                    URL url = new URL("https://emergencynumberapi.com/api/country/" + selectedCountry);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        return result.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        JSONObject countryObject = dataObject.getJSONObject("country");

                        JSONObject ambulanceObject = dataObject.getJSONObject("ambulance");
                        JSONArray ambulanceNumbersArray = ambulanceObject.getJSONArray("all");
                        String ambulance = joinJSONArray(ambulanceNumbersArray);

                        JSONObject fireObject = dataObject.getJSONObject("fire");
                        JSONArray fireNumbersArray = fireObject.getJSONArray("all");
                        String fire = joinJSONArray(fireNumbersArray);

                        JSONObject policeObject = dataObject.getJSONObject("police");
                        JSONArray policeNumbersArray = policeObject.getJSONArray("all");
                        String police = joinJSONArray(policeNumbersArray);



                        String isoCode = countryObject.getString("ISOCode");
                        String isoNumeric = countryObject.getString("ISONumeric");


                        // Similarly, you can parse other information like fire, police, etc.

                        // Now, update your text views with the obtained information
                        text1.setText(ambulance);
                        text2.setText(fire);
                        text3.setText(police);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        text1.setText("JSON parsing failed");
                    }
                } else {
                    // Handle the case where the API call failed
                    text1.setText("API call failed");
                }
            }
            private String joinJSONArray(JSONArray jsonArray) throws JSONException {
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (i > 0) {
                        result.append(", ");
                    }
                    result.append(jsonArray.getString(i));
                }
                return result.toString();
            }
        }.execute();
    }



}
