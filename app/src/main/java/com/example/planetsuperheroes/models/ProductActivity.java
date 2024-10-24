package com.example.planetsuperheroes.models;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planetsuperheroes.R;
import com.example.planetsuperheroes.SpaceItemDecoration;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProgressBar progressBar;
    private List<Product> productList = new ArrayList<>();
    private ImageView bannerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        bannerImage = findViewById(R.id.imgCategoryBanner);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        String category = getIntent().getStringExtra("category");
        Log.d("ProductActivity", "Categoría seleccionada: " + category);
        updateBanner(category);
        loadProductsFromApi(category);
    }

    private void loadProductsFromApi(String category) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        Call<List<Product>> call = apiService.getProducts(null);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                progressBar.setVisibility(View.GONE);
                if (!response.isSuccessful()) {
                    Toast.makeText(ProductActivity.this, "Código de error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Product> products = response.body();
                if (products != null && !products.isEmpty()) {
                    filterProductsByCategory(products, category);
                } else {
                    Toast.makeText(ProductActivity.this, "No se encontraron productos.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("ProductActivity", "Error al cargar productos", t);
                Toast.makeText(ProductActivity.this, "Error al cargar productos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterProductsByCategory(List<Product> products, String category) {
        productList.clear();
        for (Product product : products) {
            String categoryName = (product.getCategory() == 1) ? "Marvel" : "DC";
            if (categoryName.equals(category)) {
                productList.add(product);
            }
        }

        if (!productList.isEmpty()) {
            productAdapter = new ProductAdapter(productList, ProductActivity.this);
            recyclerView.setAdapter(productAdapter);
        } else {
            Toast.makeText(ProductActivity.this, "No se encontraron productos para la categoría seleccionada.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateBanner(String category) {
        if ("Marvel".equals(category)) {
            bannerImage.setImageResource(R.drawable.marvellogo);
        } else if ("DC".equals(category)) {
            bannerImage.setImageResource(R.drawable.dclogo);
        }
    }
}