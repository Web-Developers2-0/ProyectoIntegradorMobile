package com.example.planetsuperheroes.models;

import com.example.planetsuperheroes.models.OrderItem;

import java.util.List;

public class Order {
    private int id_order;
    private String user;
    private String state;
    private String order_date;
    private String payment_method;
    private String shipping_method;
    private String payment_status;
    private String total_amount;
    private List<OrderItem> order_items;

    // Getters y setters para cada campo
    public int getIdOrder() { return id_order; }
    public void setIdOrder(int id_order) { this.id_order = id_order; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getOrderDate() { return order_date; }
    public void setOrderDate(String order_date) { this.order_date = order_date; }

    public String getPaymentMethod() { return payment_method; }
    public void setPaymentMethod(String payment_method) { this.payment_method = payment_method; }

    public String getShippingMethod() { return shipping_method; }
    public void setShippingMethod(String shipping_method) { this.shipping_method = shipping_method; }

    public String getPaymentStatus() { return payment_status; }
    public void setPaymentStatus(String payment_status) { this.payment_status = payment_status; }

    public String getTotalAmount() { return total_amount; }
    public void setTotalAmount(String total_amount) { this.total_amount = total_amount; }

    public List<OrderItem> getOrderItems() { return order_items; }
    public void setOrderItems(List<OrderItem> order_items) { this.order_items = order_items; }
}

