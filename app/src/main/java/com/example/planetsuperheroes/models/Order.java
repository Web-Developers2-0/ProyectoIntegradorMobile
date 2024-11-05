package com.example.planetsuperheroes.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("id_order")
    private int idOrder; // ID de la orden

    @SerializedName("user")
    private User user; // Cambia a User en lugar de int

    @SerializedName("state")
    private String state;

    @SerializedName("order_date")
    private String orderDate; // Fecha de la orden

    @SerializedName("payment_method")
    private String paymentMethod; // Método de pago

    @SerializedName("shipping_method")
    private String shippingMethod; // Método de envío

    @SerializedName("payment_status")
    private String paymentStatus; // Estado del pago

    @SerializedName("total_amount")
    private double totalAmount; // Monto total

    @SerializedName("order_items")
    private List<OrderItem> orderItems; // Lista de ítems de la orden

    // Constructor vacío
    public Order() {
    }

    // Constructor con todos los campos
    public Order(int idOrder, User user, String state, String orderDate, String paymentMethod,
                 String shippingMethod, String paymentStatus, double totalAmount, List<OrderItem> orderItems) {
        this.idOrder = idOrder; // Agrega este campo
        this.user = user; // Cambia a User
        this.state = state;
        this.orderDate = orderDate;
        this.paymentMethod = paymentMethod;
        this.shippingMethod = shippingMethod;
        this.paymentStatus = paymentStatus;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public User getUser() {
        return user; // Cambia el tipo de retorno a User
    }

    public void setUser(User user) {
        this.user = user; // Cambia el método según el tipo de 'user'
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

    @Override
    public String toString() {
        return "Order{" +
                "idOrder=" + idOrder +
                ", user=" + user + // Incluye el objeto User en el toString
                ", state='" + state + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", shippingMethod='" + shippingMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", totalAmount=" + totalAmount +
                ", orderItems=" + orderItems +
                '}';
    }
}