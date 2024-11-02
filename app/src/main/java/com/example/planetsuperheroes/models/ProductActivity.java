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

    private static final int SPAN_COUNT = 2; // Número de columnas
    private static final String TAG = "ProductActivity";

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProgressBar progressBar;
    private List<Product> productList = new ArrayList<>();
    private ImageView bannerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Inicializa las vistas
        initViews();

        // Configura el RecyclerView
        setupRecyclerView();

        // Obtiene la categoría seleccionada
        String category = getIntent().getStringExtra("category");
        Log.d(TAG, "Categoría seleccionada: " + category);

        // Actualiza el banner y carga los productos
        updateBanner(category);
        loadProductsFromApi(category);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        bannerImage = findViewById(R.id.imgCategoryBanner);
    }

    private void setupRecyclerView() {
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        recyclerView.addItemDecoration(new SpaceItemDecoration(spaceInPixels, SPAN_COUNT));
    }

    private void loadProductsFromApi(String category) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        Call<List<Product>> call = apiService.getProducts(null); // Cambia null por el parámetro adecuado si es necesario

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                progressBar.setVisibility(View.GONE);
                if (!response.isSuccessful()) {
                    showToast("Código de error: " + response.code());
                    return;
                }

                List<Product> products = response.body();
                if (products != null && !products.isEmpty()) {
                    filterProductsByCategory(products, category);
                } else {
                    showToast("No se encontraron productos.");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Error al cargar productos", t);
                showToast("Error al cargar productos");
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
            productAdapter = new ProductAdapter(productList, this);
            recyclerView.setAdapter(productAdapter);
        } else {
            showToast("No se encontraron productos para la categoría seleccionada.");
        }
    }

    private void updateBanner(String category) {
        if (category == null) return; // Manejar null
        if ("Marvel".equals(category)) {
            bannerImage.setImageResource(R.drawable.marvellogo);
        } else if ("DC".equals(category)) {
            bannerImage.setImageResource(R.drawable.dclogo);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
