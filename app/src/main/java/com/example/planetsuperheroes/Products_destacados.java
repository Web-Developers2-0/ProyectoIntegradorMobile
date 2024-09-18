package com.example.planetsuperheroes;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.planetsuperheroes.adapters.CardAdapter;
import com.example.planetsuperheroes.models.Product;
import java.util.ArrayList;
import java.util.List;

public class Products_destacados extends AppCompatActivity {
    private List<Product> getHardcodedProductList() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Producto 1", "Descripción del producto 1", R.drawable.product_card4, 4));
        products.add(new Product("Otro Producto", "Otra descripción breve del producto.", R.drawable.batman_respaldo, 5));
        products.add(new Product("Producto 2", "Descripción del producto 2", R.drawable.product_card3, 3));
        products.add(new Product("Producto 3", "Descripción del producto 3", R.drawable.product_card2, 2));

        return products;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_destacados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar las listas de productos
        List<Product> listaDestacados = getHardcodedProductList();
        List<Product> listaPreventa = getHardcodedProductList();

        // Configurar RecyclerViews
        RecyclerView recyclerViewDestacados = findViewById(R.id.recycler_view_destacados);
        RecyclerView recyclerViewPreventa = findViewById(R.id.recycler_view_preventa);

        CardAdapter adapterDestacados = new CardAdapter(this, listaDestacados);
        CardAdapter adapterPreventa = new CardAdapter(this, listaPreventa);

        LinearLayoutManager layoutManagerDestacados = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManagerPreventa = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewDestacados.setLayoutManager(layoutManagerDestacados);
        recyclerViewDestacados.setAdapter(adapterDestacados);

        recyclerViewPreventa.setLayoutManager(layoutManagerPreventa);
        recyclerViewPreventa.setAdapter(adapterPreventa);
    }
}
