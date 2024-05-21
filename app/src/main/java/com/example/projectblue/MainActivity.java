package com.example.projectblue;

import com.example.projectblue.R;

import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.List;

public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    Toolbar toolbar;
    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=b23danhe";
    private RecyclerViewAdapter adapter;
    private ArrayList<Planta> listOfPlantor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

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

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_filter) {
                    showFilterDialog();
                }
                else if (itemId == R.id.action_home) {
                    Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.action_about) {
                    Toast.makeText(MainActivity.this, "About clicked", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
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
    }

    private void showFilterDialog() {
        String[] filterOptions = {"Option 1", "Option 2", "Option 3"};
        new MaterialAlertDialogBuilder(this)
                .setTitle("Select Filter")
                .setItems(filterOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Selected: " + filterOptions[which], Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}