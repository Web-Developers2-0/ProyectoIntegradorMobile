package com.example.planetsuperheroes;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.planetsuperheroes.adapters.CardAdapter;
import com.example.planetsuperheroes.models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class Products_destacados extends AppCompatActivity {

    private List<Product> listaDestacados;
    private List<Product> listaPreventa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_destacados);

        // Inicializar listas de productos
        listaDestacados = getListaFromJson("destacados.json");  // Asegúrate de que este archivo esté en la carpeta "assets"
        listaPreventa = getListaFromJson("preventa.json");      // Asegúrate de que este archivo esté en la carpeta "assets"

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

    // Función para cargar el JSON desde la carpeta "assets"
    private String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    // Función para convertir el JSON a una lista de productos
    private List<Product> getListaFromJson(String fileName) {
        String jsonString = loadJSONFromAsset(fileName);
        if (jsonString != null) {
            Gson gson = new Gson();
            Type productListType = new TypeToken<List<Product>>() {}.getType();
            return gson.fromJson(jsonString, productListType);
        }
        return null;
    }
}




