package com.example.planetsuperheroes.models;

import java.util.List;

public class OrderRequestBody {
    private String user; // Identificador del usuario
    private String state; // Estado del pedido
    private String paymentMethod; // Método de pago
    private String shippingMethod; // Método de envío
    private String paymentStatus; // Estado de pago
    private String totalAmount; // Total del pedido
    private List<OrderItem> orderItems; // Lista de artículos en el pedido

    public OrderRequestBody(String user, String state, String paymentMethod, String shippingMethod, String paymentStatus, String totalAmount, List<OrderItem> orderItems) {
        this.user = user;
        this.state = state;
        this.paymentMethod = paymentMethod;
        this.shippingMethod = shippingMethod;
        this.paymentStatus = paymentStatus;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
    }

    public String getUser() {
        return user;
    }

    public String getState() {
        return state;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
