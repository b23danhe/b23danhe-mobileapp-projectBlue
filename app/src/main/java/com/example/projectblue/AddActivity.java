package com.example.projectblue;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        WebView addView = findViewById(R.id.add_info);
        addView.loadUrl("file:///android_asset/addinfo.html");

        WebView omView = findViewById(R.id.add_view);
        omView.loadUrl("https://mobprog.webug.se/json-api/editresource.php");
    }
}