package com.example.projectblue;

import androidx.annotation.NonNull;

public class Planta {
    private String ID;
    private String name;
    private String type;
    private String company;
    private String location;
    private String category;
    private String auxdata;
    private int Size;
    private int cost;

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public String getImageUrl() {
        return auxdata;
    }
}
