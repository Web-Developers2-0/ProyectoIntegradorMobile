package com.example.planetsuperheroes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Inicializamos el RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtenemos la categoría seleccionada
        String category = getIntent().getStringExtra("category");

        // Cargamos los productos desde el archivo JSON
        loadProductsFromJson(category);
    }

    private void loadProductsFromJson(String category) {
        try {
            // Abrimos el archivo products.json en assets
            InputStream is = getAssets().open("products.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convertimos el archivo en una cadena de texto
            String json = new String(buffer, "UTF-8");

            // Convertimos la cadena en un objeto JSON
            JSONObject jsonObject = new JSONObject(json);
            JSONArray productsArray = jsonObject.getJSONArray("products");

            // Iteramos sobre el array de productos
            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject productObject = productsArray.getJSONObject(i);

                // Obtenemos el ID de la categoría y lo comparamos con la seleccionada
                int categoryId = productObject.getInt("category_id");
                String categoryName = (categoryId == 1) ? "Marvel" : "DC";

                // Si coincide con la categoría seleccionada, añadimos el producto a la lista
                if (categoryName.equals(category)) {
                    Product product = new Product(
                            productObject.getString("name"),
                            productObject.getString("description"),
                            productObject.getDouble("price"),
                            productObject.getInt("discount"),
                            productObject.getInt("stock"),
                            productObject.getString("image")
                    );
                    productList.add(product);
                }
            }

            // Configuramos el adaptador con la lista de productos filtrados
            productAdapter = new ProductAdapter(productList, this);
            recyclerView.setAdapter(productAdapter);

        } catch (Exception e) {
            Log.e("ProductListActivity", "Error loading products", e);
        }
    }
}