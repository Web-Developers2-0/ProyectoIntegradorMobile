package com.example.planetsuperheroes;

public class Comic {
    private String title;
    private String subtitle;
    private double rating;

    // Constructor con tres parámetros (sin imageUrl)
    public Comic(String title, String subtitle, double rating) {
        this.title = title;
        this.subtitle = subtitle;
        this.rating = rating;
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
}
