package com.example.planetsuperheroes.models;

public class OrderItem {
    private int id_order_items;
    private String product;
    private int quantity;

    // Getters y setters para cada campo
    public int getIdOrderItems() { return id_order_items; }
    public void setIdOrderItems(int id_order_items) { this.id_order_items = id_order_items; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}