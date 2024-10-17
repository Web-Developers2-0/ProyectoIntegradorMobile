package com.example.planetsuperheroes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private ImageView bannerImage;  // Añadir ImageView para el banner

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Inicializar las vistas
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        bannerImage = findViewById(R.id.imgCategoryBanner);  // Inicializa el banner aquí

        // Cambiar a GridLayoutManager con 2 columnas
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 es el número de columnas

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.space); // Define tu espacio en dimens.xml
        recyclerView.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        // Obtener la categoría seleccionada de la Intent
        String category = getIntent().getStringExtra("category");

        // Cambiar el banner basado en la categoría seleccionada
        updateBanner(category);

        // Cargar los productos desde la API
        loadProductsFromApi(category);
    }

    private void loadProductsFromApi(String category) {
        // Mostrar el ProgressBar mientras se cargan los datos
        progressBar.setVisibility(View.VISIBLE);

        // Realizar la llamada a la API para obtener todos los productos
        Call<List<Product>> call = RetrofitClient.getInstance().getApi().getProducts(null); // Enviar null si la API no requiere filtro en la llamada
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                progressBar.setVisibility(View.GONE);

                if (!response.isSuccessful()) {
                    Toast.makeText(ProductActivity.this, "Código de error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obtener la lista de productos
                List<Product> products = response.body();
                if (products != null && !products.isEmpty()) {
                    // Filtrar los productos por la categoría seleccionada
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
        productList.clear(); // Asegurarse de que la lista esté vacía antes de añadir los productos filtrados

        for (Product product : products) {
            String categoryName = (product.getCategory() == 1) ? "Marvel" : "DC";
            if (categoryName.equals(category)) {
                productList.add(product);
            }
        }

        // Configurar el adaptador con los productos filtrados
        if (!productList.isEmpty()) {
            productAdapter = new ProductAdapter(productList, ProductActivity.this);
            recyclerView.setAdapter(productAdapter);
        } else {
            Toast.makeText(ProductActivity.this, "No se encontraron productos para la categoría seleccionada.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para cambiar el banner según la categoría
    private void updateBanner(String category) {
        if ("Marvel".equals(category)) {
            bannerImage.setImageResource(R.drawable.marvellogo);  // Cambiar a la imagen de Marvel
        } else if ("DC".equals(category)) {
            bannerImage.setImageResource(R.drawable.dclogo);  // Cambiar a la imagen de DC
        }
    }
}
