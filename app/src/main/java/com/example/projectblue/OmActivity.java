package com.example.projectblue;

import android.os.Bundle;
import android.webkit.WebView;


import androidx.appcompat.app.AppCompatActivity;

public class OmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_om);

        WebView omView = findViewById(R.id.om_view);
        omView.loadUrl("file:///android_asset/about.html");
    }
}