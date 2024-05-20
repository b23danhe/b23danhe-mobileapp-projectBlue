package com.example.projectblue;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;


public class PlantaActivity extends AppCompatActivity {

    private String name;
    private String latin;
    private String family;
    private String room;
    private String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planta);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            latin = extras.getString("latin");
            family = extras.getString("family");
            room = extras.getString("room");
            image = extras.getString("image");
        }

        ImageView plantaImage = findViewById(R.id.planta_image);
        Picasso.get()
                .load(image)
                .into(plantaImage);

        TextView plantaName = findViewById(R.id.planta_name);
        plantaName.setText(name);

        TextView plantaLatin = findViewById(R.id.planta_latin);
        plantaLatin.setText(latin);

        TextView plantaFamily = findViewById(R.id.planta_family);
        plantaFamily.setText(family);

        TextView plantaRoom = findViewById(R.id.planta_room);
        plantaRoom.setText(room);
    }
}