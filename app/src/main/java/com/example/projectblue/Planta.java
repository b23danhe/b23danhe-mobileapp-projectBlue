package com.example.projectblue;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Planta {
    private String name;
    @SerializedName("company")
    private String latinName;
    private String location;
    @SerializedName("category")
    private String family;
    private String auxdata;

    public Planta(String name, String latinName, String location, String family){
        this.name = name;
        this.latinName = latinName;
        this.location = location;
        this.family = family;
    }

    //---- Getters ----
    public String getName() {
        return name;
    }

    public String getLatinName() {
        return latinName;
    }

    public String getLocation() {
        return location;
    }

    public String getFamily() {
        return family;
    }

    public String getImageUrl() {
        return auxdata;
    }

    //---- Setters ----
    public void setName(String name) {
        this.name = name;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    //---- ToString ----
    @NonNull
    @Override
    public String toString() {
        return name;
    }

}
