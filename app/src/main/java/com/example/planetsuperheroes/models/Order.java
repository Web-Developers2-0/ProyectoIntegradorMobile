package com.example.planetsuperheroes.models;

import java.util.List;

public class Order {
    private int idOrder; // ID de la orden
    private String user; // ID del usuario
    private String state; // Estado de la orden
    private String orderDate; // Fecha de la orden
    private String paymentMethod; // Método de pago
    private String shippingMethod; // Método de envío
    private String paymentStatus; // Estado del pago
    private double totalAmount; // Monto total
    private List<OrderItem> orderItems; // Lista de ítems de la orden

    // Constructor, Getters y Setters
    public Order(int idOrder, String user, String state, String orderDate, String paymentMethod,
                 String shippingMethod, String paymentStatus, double totalAmount, List<OrderItem> orderItems) {
        this.idOrder = idOrder;
        this.user = user;
        this.state = state;
        this.orderDate = orderDate;
        this.paymentMethod = paymentMethod;
        this.shippingMethod = shippingMethod;
        this.paymentStatus = paymentStatus;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
    }

    // Getters y Setters...

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}