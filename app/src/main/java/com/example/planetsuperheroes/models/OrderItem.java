package com.example.planetsuperheroes.models;

import com.google.gson.annotations.SerializedName;

public class OrderItem {
    @SerializedName("id_product") // Este es el ID del producto que estás enviando
    private int product; // ID del producto
    private String productName; // Nombre del producto para mostrar en la UI
    @SerializedName("quantity")
    private int quantity; // Cantidad del producto

    // Constructor
    public OrderItem(int product, String productName, int quantity) {
        this.product = product; // ID del producto
        this.productName = productName; // Nombre del producto
        this.quantity = quantity; // Cantidad del producto
    }

    // Getters
    public int getProduct() {
        return product;
    }

    public String getProductName() {
        return productName; // Método para obtener el nombre del producto
    }

    public int getQuantity() {
        return quantity;
    }
}
