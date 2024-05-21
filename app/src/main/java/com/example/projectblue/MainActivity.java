package com.example.projectblue;

import com.example.projectblue.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=b23danhe";
    private RecyclerViewAdapter adapter;
    private ArrayList<Planta> listOfPlantor = new ArrayList<>();
    private String[] filterOptions;
    private SharedPreferences myPreferenceRef;
    private SharedPreferences.Editor myPreferenceEditor;
    private static final String PREFERENCE_NAME = "MyPreferences";
    private static final String FILTER_KEY = "preferredFilter";
    private String currentFilter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        myPreferenceRef = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        myPreferenceEditor = myPreferenceRef.edit();

        adapter = new RecyclerViewAdapter(this, listOfPlantor, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(Planta planta) {
                Intent intent = new Intent(MainActivity.this, PlantaActivity.class);
                intent.putExtra("name", planta.getName());
                intent.putExtra("latin", planta.getLatinName());
                intent.putExtra("room", planta.getLocation());
                intent.putExtra("family", planta.getFamily());
                intent.putExtra("image", planta.getImageUrl());

                Log.d("Planta1" , planta.toString());

                startActivity(intent);
            }
        });

        new JsonTask(this).execute(JSON_URL); //Hämtar en JSON textfil från en URL

        RecyclerView view = findViewById(R.id.recycler_view);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);

        // The navigationBar on the bottom of the screen
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_filter) {
                    showFilterDialog();
                }
                else if (itemId == R.id.action_home) {
                    Toast.makeText(MainActivity.this, "Hem clicked", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.action_about) {
                    Intent intent = new Intent(MainActivity.this, OmActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });

        // Load preferred filter and apply it
        String preferredFilter = myPreferenceRef.getString(FILTER_KEY, null);
        if (preferredFilter != null) {
            currentFilter = preferredFilter.equals("Rensa filter") ? null : preferredFilter;
            adapter.filter(preferredFilter);
        }
    }

    @Override
    public void onPostExecute(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Planta>>() {}.getType();
        listOfPlantor = gson.fromJson(json, type);
        Log.d("Plantor", "" + listOfPlantor.size());

       adapter.update(listOfPlantor);  //Updaterar listan med plantor
       adapter.notifyDataSetChanged();  //Refreshar RecyclerView så att den nya listan med plantor visas

        for (Planta planta : listOfPlantor){
            Log.d("Planta" , planta.toString());
            Log.d("Planta", planta.getImageUrl());
        }
        Log.d("Plantor", json);

        // Extract unique locations
        HashSet<String> uniqueLocations = new HashSet<>();
        for (Planta planta : listOfPlantor) {
            uniqueLocations.add(planta.getLocation());
        }
        uniqueLocations.add("Rensa filter"); // Add an option to clear the filter

        // Convert the set to a list and then to an array
        filterOptions = new ArrayList<>(uniqueLocations).toArray(new String[0]);

        // Reapply the current filter if it exists
        if (currentFilter != null && !currentFilter.equals("Rensa filter")) {
            adapter.filter(currentFilter);
            Log.d("DagensFilter", currentFilter);
        }
    }

    private void showFilterDialog() {
        if (filterOptions == null || filterOptions.length == 0) {
            Toast.makeText(this, "No filter options available", Toast.LENGTH_SHORT).show();
            return;
        }

        new MaterialAlertDialogBuilder(this)
                .setTitle("Select Filter")
                .setItems(filterOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedFilter = filterOptions[which];
                        adapter.filter(selectedFilter); // Apply the filter
                        Toast.makeText(MainActivity.this, "Selected: " + selectedFilter, Toast.LENGTH_SHORT).show();

                        // Save the selected filter
                        myPreferenceEditor.putString(FILTER_KEY, selectedFilter);
                        myPreferenceEditor.apply();
                    }
                })
                .show();
    }
}