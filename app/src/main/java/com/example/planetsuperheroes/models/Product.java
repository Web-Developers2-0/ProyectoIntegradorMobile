package com.example.planetsuperheroes.models;

public class Product {
    private String title;
    private String description;
    private String imageUrl;
    private double rating;

    public Product(String title, String description, String imageUrl, double rating) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
