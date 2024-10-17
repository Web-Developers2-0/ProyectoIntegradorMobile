package com.example.planetsuperheroes;

public class Category {
    private int id_category;
    private String name;

    // Constructor vacío requerido para Gson
    public Category() {
    }

    // Getters
    public int getId_category() {
        return id_category;
    }

    public String getName() {
        return name;
    }
}
