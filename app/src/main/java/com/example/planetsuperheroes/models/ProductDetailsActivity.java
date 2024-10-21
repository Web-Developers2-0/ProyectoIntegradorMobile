package com.example.planetsuperheroes.models;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.planetsuperheroes.R;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.network.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productNameTextView, productDescriptionTextView, productPriceTextView, quantityTextView, productStockTextView;
    private Button incrementButton, decrementButton, addToCartButton, orderButton;

    private int quantity = 0;
    private CartService cartService;
    private int productId;
    private int stock;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        cartService = new CartService();
        initializeViews();
        productId = getIntent().getIntExtra("productId", -1);

        if (productId == -1) {
            Toast.makeText(this, "ID de producto no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadProductFromApi();
        updateQuantityTextView();
        setupButtonListeners();
    }

    private void initializeViews() {
        productImageView = findViewById(R.id.productImageView);
        productNameTextView = findViewById(R.id.productNameTextView);
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        quantityTextView = findViewById(R.id.quantityTextView);
        productStockTextView = findViewById(R.id.productStockTextView);
        incrementButton = findViewById(R.id.incrementButton);
        decrementButton = findViewById(R.id.decrementButton);
        addToCartButton = findViewById(R.id.addToCartButton);
        orderButton = findViewById(R.id.orderButton);
    }

    private void setupButtonListeners() {
        incrementButton.setOnClickListener(v -> {
            if (quantity < stock) {
                quantity++;
                updateQuantityTextView();
            } else {
                Toast.makeText(this, "No hay suficiente stock disponible", Toast.LENGTH_SHORT).show();
            }
        });

        decrementButton.setOnClickListener(v -> {
            if (quantity > 0) {
                quantity--;
                updateQuantityTextView();
            }
        });

        addToCartButton.setOnClickListener(v -> {
            if (quantity > 0) {
                addToCart();
            } else {
                Toast.makeText(this, "Seleccione una cantidad válida", Toast.LENGTH_SHORT).show();
            }
        });

        orderButton.setOnClickListener(v -> sendOrder());
    }

    private void addToCart() {
        cartService.addToCart(new Product(
                productId,
                productNameTextView.getText().toString(),
                productDescriptionTextView.getText().toString(),
                Double.parseDouble(productPriceTextView.getText().toString().replace("$", "")),
                "", // URL de la imagen
                0.0, // Calificación
                stock,
                1 // Categoría
        ), quantity);
        Toast.makeText(this, "Agregado al carrito", Toast.LENGTH_SHORT).show();
    }

    private void loadProductFromApi() {
        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        Call<Product> call = apiService.getProduct(productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Product product = response.body();
                    stock = product.getStock();
                    updateUI(product);
                } else {
                    Log.e("API Error", "Error: " + response.code() + ", Mensaje: " + response.message());
                    Toast.makeText(ProductDetailsActivity.this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e("ProductDetailsActivity", "Error al cargar el producto", t);
                Toast.makeText(ProductDetailsActivity.this, "Error al cargar el producto", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(Product product) {
        productNameTextView.setText(product.getName());
        productDescriptionTextView.setText(product.getDescription());
        productPriceTextView.setText("$" + product.getPrice());
        Glide.with(this).load(product.getImage()).into(productImageView);
        productStockTextView.setText("Stock: " + stock);
    }

    private void updateQuantityTextView() {
        quantityTextView.setText(String.valueOf(quantity));
    }


    private void sendOrder() {
        if (quantity > 0 && quantity <= stock) {
            List<Map<String, Object>> orderItems = new ArrayList<>();
            Map<String, Object> orderItem = new HashMap<>();
            orderItem.put("product", productId);
            orderItem.put("quantity", quantity);
            orderItems.add(orderItem);

            Map<String, Object> orderRequestBody = new HashMap<>();

            String userId = getUserId(); // Obtiene el ID del usuario desde SharedPreferences
            String token = getUserToken(); // Obtiene el token JWT

            // Asegúrate de que userId y token no sean nulos
            if (userId == null) {
                Toast.makeText(this, "ID de usuario no disponible", Toast.LENGTH_SHORT).show();
                return;
            }

            if (token == null) {
                Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
                return;
            }

            orderRequestBody.put("user", userId);
            orderRequestBody.put("state", "pending");
            orderRequestBody.put("total_amount", String.valueOf(quantity * Double.parseDouble(productPriceTextView.getText().toString().replace("$", ""))));
            orderRequestBody.put("order_items", orderItems);

            // Imprimir el cuerpo del pedido para depuración
            Log.d("Order Request", "Cuerpo del pedido: " + orderRequestBody.toString());

            // Llamar a createOrder pasando el token
            createOrder(orderRequestBody, token);
        } else {
            Toast.makeText(this, "Cantidad no válida para realizar el pedido", Toast.LENGTH_SHORT).show();
        }
    }



    private void createOrder(Map<String, Object> orderRequestBody, String token) {
        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        Call<Order> call = apiService.createOrder("Bearer " + token, orderRequestBody);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductDetailsActivity.this, "Pedido realizado con éxito!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Order Error", "Error al realizar el pedido: " + response.code());
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.e("Order Failure", "Fallo en la conexión: ", t);
                Toast.makeText(ProductDetailsActivity.this, "Error de conexión al realizar el pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void handleErrorResponse(Response<Order> response) {
        try {
            if (response.errorBody() != null) {
                Log.e("Order Error", "Cuerpo del error: " + response.errorBody().string());
            }
        } catch (IOException e) {
            Log.e("Order Error", "Error al leer el cuerpo del error", e);
        }
        Toast.makeText(ProductDetailsActivity.this, "Error al realizar el pedido", Toast.LENGTH_SHORT).show();
    }


    private String getUserToken() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return prefs.getString("jwt_token", null); // Asegúrate de que "jwt_token" esté almacenado correctamente
    }

    private String getUserId() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return prefs.getString("user_id", null); // Cambia "user_id" por la clave que usaste para almacenar el ID del usuario
    }

}
