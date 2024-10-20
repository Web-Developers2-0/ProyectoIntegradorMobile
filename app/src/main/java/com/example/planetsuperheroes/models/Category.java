package com.example.planetsuperheroes.models;


import java.util.List;

public class Category {
    private int id_category;
    private String name;

    // Constructor vacío requerido para Gson
    public Category() {
    }

    // Getters
    public int getId_category() {
        return id_category;
    }

    public String getName() {
        return name;
    }

    public static class Order {
        private int idOrder; // Cambia esto para usar el ID real si es necesario
        private int user;
        private String state;
        private long orderDate; // Puedes usar Date o Long (timestamp)
        private String paymentMethod;
        private String shippingMethod;
        private String paymentStatus;
        private double totalAmount;

        // Constructor, Getters y Setters
        public Order(int idOrder, int user, String state, long orderDate, String paymentMethod, String shippingMethod, String paymentStatus, double totalAmount) {
            this.idOrder = idOrder;
            this.user = user;
            this.state = state;
            this.orderDate = orderDate;
            this.paymentMethod = paymentMethod;
            this.shippingMethod = shippingMethod;
            this.paymentStatus = paymentStatus;
            this.totalAmount = totalAmount;
        }

        // Getters y Setters...
    }
}
