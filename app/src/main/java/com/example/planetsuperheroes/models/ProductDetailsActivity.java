package com.example.planetsuperheroes.models;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.planetsuperheroes.R;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.network.RetrofitClient;

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

        // Obtener el ID del producto pasado desde la actividad anterior
        productId = getIntent().getIntExtra("productId", -1);
        Log.d("ProductDetailsActivity", "ID del producto recibido: " + productId);

        if (productId == -1) {
            Toast.makeText(this, "ID de producto no válido", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad si el ID no es válido
            return;
        }

        // Llamada a la API para obtener el producto
        loadProductFromApi();

        updateQuantityTextView();

        incrementButton.setOnClickListener(v -> {
            if (quantity < stock) {
                quantity++;
                updateQuantityTextView();
            } else {
                Toast.makeText(ProductDetailsActivity.this, "No hay suficiente stock disponible", Toast.LENGTH_SHORT).show();
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
                cartService.addToCart(new Product(
                        productId,
                        productNameTextView.getText().toString(),
                        productDescriptionTextView.getText().toString(),
                        Double.parseDouble(productPriceTextView.getText().toString().replace("$", "")),
                        "", // La URL de la imagen
                        0.0, // Calificación, si la tienes
                        stock,
                        1 // Asumir categoría como 1
                ), quantity);
                Toast.makeText(ProductDetailsActivity.this, "Agregado al carrito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProductDetailsActivity.this, "Seleccione una cantidad válida", Toast.LENGTH_SHORT).show();
            }
        });

        orderButton.setOnClickListener(v -> {
            double totalPrice = Double.parseDouble(productPriceTextView.getText().toString().replace("$", "")) * quantity;
            sendOrder(totalPrice);
        });
    }

    private void loadProductFromApi() {
        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        Call<Product> call = apiService.getProduct(productId); // Usa productId aquí
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Log.d("API Response", "Código: " + response.code() + ", Cuerpo: " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    Product product = response.body();
                    stock = product.getStock(); // Almacena el stock
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
        productStockTextView.setText("Stock: " + stock); // Muestra el stock
    }

    private void updateQuantityTextView() {
        quantityTextView.setText(String.valueOf(quantity));
    }

    private void sendOrder(double totalPrice) {
        if (quantity > 0 && quantity <= stock) {
            // Reduce el stock localmente
            stock -= quantity;
            productStockTextView.setText("Stock: " + stock); // Actualiza el stock en la UI

            // Crea un objeto Product con todos los campos requeridos
            Product updatedProduct = new Product();
            updatedProduct.setId(productId); // Establece el ID del producto
            updatedProduct.setStock(stock); // Establece el nuevo stock
            updatedProduct.setCategory(1); // Asegúrate de establecer una categoría válida (1 o 2)
            updatedProduct.setName(productNameTextView.getText().toString()); // Establece el nombre del producto
            updatedProduct.setDescription(productDescriptionTextView.getText().toString()); // Establece la descripción

            // Actualiza el stock en el servidor
            updateStockInServer(updatedProduct);

            Toast.makeText(this, "Total a pagar: $" + totalPrice + ". Stock actualizado: " + stock, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cantidad no válida para realizar el pedido", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStockInServer(Product updatedProduct) {
        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        Call<Product> call = apiService.updateProductStock(updatedProduct.getId(), updatedProduct); // Envía el objeto Product
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Log.d("Stock Update", "Stock actualizado en el servidor: " + updatedProduct.getStock());
                } else {
                    Log.e("Stock Update Error", "Error al actualizar el stock: " + response.code());
                    Toast.makeText(ProductDetailsActivity.this, "Error al actualizar el stock en el servidor", Toast.LENGTH_SHORT).show();
                    try {
                        if (response.errorBody() != null) {
                            Log.e("Stock Update Error", "Cuerpo del error: " + response.errorBody().string());
                        }
                    } catch (IOException e) {
                        Log.e("Stock Update Error", "Error al leer el cuerpo del error", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e("Stock Update Failure", "Fallo en la conexión: ", t);
                Toast.makeText(ProductDetailsActivity.this, "Error de conexión al actualizar el stock", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
