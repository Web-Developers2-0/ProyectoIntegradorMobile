package com.example.planetsuperheroes.models;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView productNameTextView, productDescriptionTextView, productPriceTextView, productStockTextView;

    private int productId;
    private int stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initializeViews();
        productId = getIntent().getIntExtra("productId", -1);

        if (productId == -1) {
            Toast.makeText(this, "ID de producto no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadProductFromApi();
    }

    private void initializeViews() {
        productImageView = findViewById(R.id.productImageView);
        productNameTextView = findViewById(R.id.productNameTextView);
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        productStockTextView = findViewById(R.id.productStockTextView);
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
}
