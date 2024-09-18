package com.example.planetsuperheroes;

import android.content.Context;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.planetsuperheroes.adapters.CardAdapter;
import com.example.planetsuperheroes.models.Product;
import java.util.List;

public class Products_destacados extends AppCompatActivity {

    private List<Product> listaDestacados;
    private List<Product> listaPreventa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_products_destacados);

        // Ajuste de padding para el diseño principal en relación a las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar listas de productos
        listaDestacados = getListaDestacados();
        listaPreventa = getListaPreventa();

        // Configuración de RecyclerViews
        RecyclerView recyclerViewDestacados = findViewById(R.id.recycler_view_destacados);
        RecyclerView recyclerViewPreventa = findViewById(R.id.recycler_view_preventa);

        // Configurar adaptadores
        recyclerViewDestacados.setAdapter(new CardAdapter(listaDestacados, this));
        recyclerViewPreventa.setAdapter(new CardAdapter(listaPreventa, this));

        // LinearLayoutManager con orientación horizontal
        LinearLayoutManager layoutManagerDestacados = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManagerPreventa = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        // Asignar los LayoutManagers a los RecyclerView
        recyclerViewDestacados.setLayoutManager(layoutManagerDestacados);
        recyclerViewPreventa.setLayoutManager(layoutManagerPreventa);
    }

    // Métodos para obtener las listas de productos (debes implementar estos métodos)
    private List<Product> getListaDestacados() {
        // Retorna la lista de productos destacados
        // Aquí debes cargar tus datos. Ejemplo:
        // return Arrays.asList(new Product("Title1", "Desc1", "ImageUrl1", 4.5));
        return null;
    }

    private List<Product> getListaPreventa() {
        // Retorna la lista de productos en preventa
        // Aquí debes cargar tus datos. Ejemplo:
        // return Arrays.asList(new Product("Title2", "Desc2", "ImageUrl2", 4.7));
        return null;
    }
}


