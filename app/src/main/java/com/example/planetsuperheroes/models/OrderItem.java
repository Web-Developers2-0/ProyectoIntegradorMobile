package com.example.planetsuperheroes.models;

public class OrderItem {
    private int productId; // ID del producto
    private String product; // Nombre del producto
    private int quantity; // Cantidad

    public OrderItem(int productId, String product, int quantity) {
        this.productId = productId;
        this.product = product;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
