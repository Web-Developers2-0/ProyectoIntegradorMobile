package com.example.planetsuperheroes.models;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id_product")
    private int id;
    private String name;
    private String description;
    private double price;
    private String image;
    private double calification;
    private int stock;
    private int category; // Asegúrate de incluir este campo

    // Constructor con todos los parámetros
    public Product(int id, String name, String description, double price, String image, double calification, int stock, int category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.calification = calification;
        this.stock = stock;
        this.category = category;
    }

    // Constructor vacío
    public Product() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public double getCalification() {
        return calification; // Getter para la calificación
    }

    public int getStock() {
        return stock; // Getter para el stock
    }

    public int getCategory() {
        return category; // Getter para la categoría
    }

    // Setters
    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(int category) {
        this.category = category; // Setter para la categoría
    }

    public void setName(String name) {
        this.name = name; // Setter para el nombre
    }

    public void setDescription(String description) {
        this.description = description; // Setter para la descripción
    }
}

