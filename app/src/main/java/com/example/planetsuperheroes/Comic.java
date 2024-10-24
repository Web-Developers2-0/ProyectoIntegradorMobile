package com.example.planetsuperheroes;

public class Comic {
    private String title;
    private String subtitle;
    private double rating;
    private int imageResId; // Para almacenar la referencia a la imagen

    // Constructor actualizado
    public Comic(String title, String subtitle, double rating, int imageResId) {
        this.title = title;
        this.subtitle = subtitle;
        this.rating = rating;
        this.imageResId = imageResId; // Asignar el valor de la imagen
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public double getRating() {
        return rating;
    }

    public int getImageResId() {
        return imageResId;
    }
}