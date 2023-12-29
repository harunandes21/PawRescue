package com.example.pawrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class LoadingActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        webView = findViewById(R.id.webView);

        // Load GIF with appropriate settings
        String html = "<html><body style='margin:0;padding:0;'><img src='loading.gif' width='100%' height='100%'/></body></html>";
        webView.loadDataWithBaseURL("file:///android_res/drawable/", html, "text/html", "utf-8", null);

        // Adjust WebView settings to fit the screen
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webView.setInitialScale(1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 500);
    }
}