package com.example.planetsuperheroes;

import com.example.planetsuperheroes.models.Product;

import java.util.HashMap;
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
        // Usar get y verificar si es null
        int currentQuantity = cartItems.get(product) != null ? cartItems.get(product) : 0;
        currentQuantity += quantity;
        cartItems.put(product, currentQuantity);
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
        // Usar get y verificar si es null
        return cartItems.get(product) != null ? cartItems.get(product) : 0;
    }

    // Método para limpiar el carrito
    public void clearCart() {
        cartItems.clear(); // Limpia todos los productos del carrito
    }
}
