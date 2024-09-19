package com.example.planetsuperheroes;

public class Product {
    private String name;
    private String description;
    private double price;
    private int discount;
    private int stock;
    private String image;

    public Product(String name, String description, double price, int discount, int stock, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
        this.image = image;
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

    public int getDiscount() {
        return discount;
    }

    public int getStock() {
        return stock;
    }

    public String getImage() {
        return image;
    }
}


