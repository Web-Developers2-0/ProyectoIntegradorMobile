package com.example.planetsuperheroes.models;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    private List<CarItem> cartItems = new ArrayList<>();

    public void addToCart(Product product, int quantity) {
        for (CarItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CarItem(product, quantity));
    }

    public void removeFromCart(Product product, int quantity) {
        for (CarItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                int newQuantity = item.getQuantity() - quantity;
                if (newQuantity <= 0) {
                    cartItems.remove(item);
                } else {
                    item.setQuantity(newQuantity);
                }
                return;
            }
        }
    }

    public List<CarItem> getItems() {
        return cartItems;
    }

    public double getTotalAmount() {
        double total = 0;
        for (CarItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
}
