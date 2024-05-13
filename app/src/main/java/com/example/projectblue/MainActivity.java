package com.example.projectblue;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=b23danhe";
    private RecyclerViewAdapter adapter;
    private ArrayList<Planta> listOfPlantor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new RecyclerViewAdapter(this, listOfPlantor, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(Planta planta) {
                Toast.makeText(MainActivity.this, planta.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Planta1" , planta.toString());
            }
        });

        new JsonTask(this).execute(JSON_URL); //Hämtar en JSON textfil från en URL

        RecyclerView view = findViewById(R.id.recycler_view);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
    }

    @Override
    public void onPostExecute(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Planta>>() {}.getType();
        listOfPlantor = gson.fromJson(json, type);
        Log.d("Mountains", "" + listOfPlantor.size());

        adapter.update(listOfPlantor);  //Updaterar listan med mountains
        adapter.notifyDataSetChanged();  //Refreshar RecyclerView så att den nya listan med berg visas

        for (Planta planta : listOfPlantor){
            Log.d("Planta" , planta.toString());
        }
        Log.d("Plantor", json);
    }
}