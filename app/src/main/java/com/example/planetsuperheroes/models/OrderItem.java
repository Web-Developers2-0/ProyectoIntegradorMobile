package com.example.planetsuperheroes.models;

import com.google.gson.annotations.SerializedName;

public class OrderItem {
    @SerializedName("id_product") // Este es el ID del producto que estás enviando
    private int product; // Cambiado a 'product' para reflejar mejor el uso
    @SerializedName("quantity")
    private int quantity;

    public OrderItem(int product, int quantity) { // Cambia String a int
        this.product = product; // Asegúrate de que esto se corresponda con el ID del producto
        this.quantity = quantity;
    }

    // Getters y Setters
    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}