package com.example.planetsuperheroes;

import com.example.planetsuperheroes.models.OrderItem;
import com.example.planetsuperheroes.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartManager {
    private static CartManager instance;
    private Map<Product, Integer> cartItems;

    private CartManager() {
        cartItems = new HashMap<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // Método para agregar un producto al carrito con una cantidad específica
    public void addProductToCart(Product product, int quantity) {
        // Obtener la cantidad actual del producto en el carrito
        int currentQuantity = cartItems.getOrDefault(product, 0);

        // Verifica si la cantidad total (actual + nueva) no supera el stock disponible
        if (currentQuantity + quantity > product.getStock()) {
            throw new IllegalArgumentException("No hay suficiente stock disponible para " + product.getName());
        }

        // Actualiza la cantidad en el carrito
        cartItems.put(product, currentQuantity + quantity);
    }

    // Método para obtener la lista de productos en el carrito
    public Map<Product, Integer> getCartItems() {
        return cartItems;
    }

    // Método para obtener el total de productos en el carrito
    public int getCartItemCount() {
        int count = 0;
        for (int quantity : cartItems.values()) {
            count += quantity;
        }
        return count;
    }

    // Método para obtener la cantidad de un producto específico
    public int getProductQuantity(Product product) {
        return cartItems.getOrDefault(product, 0);
    }

    // Método para limpiar el carrito
    public void clearCart() {
        cartItems.clear();
    }

    // Método para obtener la lista de OrderItems a partir de los cartItems
    public List<OrderItem> getOrderItems() {
        List<OrderItem> orderItems = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            // Crear un OrderItem usando el ID y nombre del producto
            orderItems.add(new OrderItem(product.getId(), product.getName(), quantity)); // Asegúrate de que Product tenga getId() y getName()
        }
        return orderItems;
    }
}
