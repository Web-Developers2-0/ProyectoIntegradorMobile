package com.example.planetsuperheroes.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OrderRequestBody {
    @SerializedName("order_items")
    private List<OrderItem> orderItems;

    public OrderRequestBody(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
