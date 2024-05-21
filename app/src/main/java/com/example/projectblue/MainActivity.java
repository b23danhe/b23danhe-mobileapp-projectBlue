package com.example.projectblue;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    private String currentFilter = null;

    // SharedPreferences to store user preferences
    private SharedPreferences myPreferenceRef;
    private SharedPreferences.Editor myPreferenceEditor;
    private static final String PREFERENCE_NAME = "MyPreferences";
    private static final String FILTER_KEY = "preferredFilter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        // Initialize SharedPreferences
        myPreferenceRef = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        myPreferenceEditor = myPreferenceRef.edit();

        // Set up the RecyclerView with an adapter
        adapter = new RecyclerViewAdapter(this, listOfPlantor, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(Planta planta) {
                // Start the PlantaActivity and pass the planta details
                Intent intent = new Intent(MainActivity.this, PlantaActivity.class);
                intent.putExtra("name", planta.getName());
                intent.putExtra("latin", planta.getLatinName());
                intent.putExtra("room", planta.getLocation());
                intent.putExtra("family", planta.getFamily());
                intent.putExtra("image", planta.getImageUrl());

                startActivity(intent);
            }
        });

        // Execute the JSON to fetch data from a URL
        new JsonTask(this).execute(JSON_URL);

        // Set up the RecyclerView layout and adapter
        RecyclerView view = findViewById(R.id.recycler_view);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);

        // Set up the bottom navigation bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_filter) {
                    showFilterDialog();
                }
                else if (itemId == R.id.action_add) {
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    startActivity(intent);
                }
                else if (itemId == R.id.action_about) {
                    Intent intent = new Intent(MainActivity.this, OmActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });

        // Load and apply the preferred filter if it exists
        String preferredFilter = myPreferenceRef.getString(FILTER_KEY, null);
        if (preferredFilter != null) {
            currentFilter = preferredFilter.equals("Rensa filter") ? null : preferredFilter;
            adapter.filter(preferredFilter);
        }
    }

    @Override
    public void onPostExecute(String json) {

        // Create GSON object and unmarshall list of Planta objects
        Gson gson = new Gson();
        Type type = new TypeToken<List<Planta>>() {}.getType();
        listOfPlantor = gson.fromJson(json, type);

        // Update the adapter with the new list of Plantor
        // and refresh RecyclerView to show the new list of Plantor
        adapter.update(listOfPlantor);
        adapter.notifyDataSetChanged();


        // Extract unique locations for filtering options
        HashSet<String> uniqueLocations = new HashSet<>();
        for (Planta planta : listOfPlantor) {
            uniqueLocations.add(planta.getLocation());
        }

        // Add an option to clear the filter
        uniqueLocations.add("Rensa filter");

        // Convert the set to a list and then to an array
        filterOptions = new ArrayList<>(uniqueLocations).toArray(new String[0]);

        // Reapply the current filter if it exists
        if (currentFilter != null && !currentFilter.equals("Rensa filter")) {
            adapter.filter(currentFilter);
        }
    }

    private void showFilterDialog() {
        if (filterOptions == null || filterOptions.length == 0) {
            Toast.makeText(this, "No filter options available", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show the filter dialog with the available options
        new MaterialAlertDialogBuilder(this)
                .setTitle("Select Filter")
                .setItems(filterOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedFilter = filterOptions[which];
                        // Apply the selected filter
                        adapter.filter(selectedFilter);

                        // Save the selected filter in SharedPreferences
                        myPreferenceEditor.putString(FILTER_KEY, selectedFilter);
                        myPreferenceEditor.apply();
                    }
                })
                .show();
    }
}