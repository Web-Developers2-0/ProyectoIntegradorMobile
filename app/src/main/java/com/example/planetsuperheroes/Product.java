package com.example.planetsuperheroes;

public class Product {
    private int id_product; // Debe coincidir con el nombre en el JSON
    private String name;
    private String description;
    private double price; // Asegúrate de que el JSON tenga un valor numérico
    private int discount;
    private int stock;
    private String image;
    private int pages;
    private String format;
    private double weight; // Asegúrate de que el JSON tenga un valor numérico
    private String isbn;
    private int category; // Cambiado a int para representar solo el ID de la categoría
    private double calification; // Cambia a String si el JSON lo requiere

    public Product() {
        // Constructor vacío requerido para Gson
    }

    // Getters
    public int getId_product() { return id_product; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getDiscount() { return discount; }
    public int getStock() { return stock; }
    public String getImage() { return image; }
    public int getPages() { return pages; }
    public String getFormat() { return format; }
    public double getWeight() { return weight; }
    public String getIsbn() { return isbn; }
    public int getCategory() { return category; } // Método actualizado
    public double getCalification() { return calification; }

    // Setters
    public void setId_product(int id_product) { this.id_product = id_product; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setDiscount(int discount) { this.discount = discount; }
    public void setStock(int stock) { this.stock = stock; }
    public void setImage(String image) { this.image = image; }
    public void setPages(int pages) { this.pages = pages; }
    public void setFormat(String format) { this.format = format; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setCategory(int category) { this.category = category; } // Método actualizado
    public void setCalification(double calification) { this.calification = calification; }
}
