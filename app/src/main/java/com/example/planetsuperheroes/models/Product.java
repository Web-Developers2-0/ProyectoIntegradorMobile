package com.example.planetsuperheroes.models;

public class Product {
    private int idProduct;
    private String name;
    private String description;
    private double price;
    private Integer discount; // Usar Integer en lugar de int para valores nulos
    private int stock;
    private String image; // Cambié esto a String para que coincida con el JSON
    private Integer pages; // Usar Integer en lugar de int para valores nulos
    private String format;
    private Double weight; // Usar Double en lugar de double para valores nulos
    private String isbn;
    private Integer calification; // Usar Integer en lugar de int para valores nulos
    private int category; // Cambié esto a int para el ID de la categoría

    // Constructor vacío requerido por Gson
    public Product() {}

    public Product(int idProduct, String name, String description, double price, Integer discount, int stock,
                   String image, Integer pages, String format, Double weight, String isbn, Integer calification, int category) {
        this.idProduct = idProduct;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
        this.image = image;
        this.pages = pages;
        this.format = format;
        this.weight = weight;
        this.isbn = isbn;
        this.calification = calification;
        this.category = category;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getCalification() {
        return calification;
    }

    public void setCalification(Integer calification) {
        this.calification = calification;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
