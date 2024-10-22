package com.example.planetsuperheroes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ImageButton btnUser;
    private ImageButton btnNotification;
    private RecyclerView recyclerViewComics;
    private ComicAdapter comicAdapter;
    private LinearLayoutManager layoutManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Inicializar vistas
        btnUser = findViewById(R.id.btnUser);
        btnNotification = findViewById(R.id.btnNotification);
        // recyclerViewComics = findViewById(R.id.recyclerViewComics);
        Button btnSeeMore = findViewById(R.id.btnSeeMore);

        // Configuración de los botones de usuario y notificación
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });

        // Configura el listener para el botón de Marvel
        ImageButton btnMarvel = findViewById(R.id.btnMarvel);
        btnMarvel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductActivity("Marvel");
            }
        });

        // Configura el listener para el botón de DC
        ImageButton btnDC = findViewById(R.id.btnDC);
        btnDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductActivity("DC");
            }
        });

        // Configuración del RecyclerView para el carrusel de cómics
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewComics.setLayoutManager(layoutManager);

        // Crear lista de cómics hardcodeados
        List<Comic> comicList = new ArrayList<>();
        comicList.add(new Comic("Batman y Robin", "Hunters or Hunted?", 4.9));
        comicList.add(new Comic("Gwenpool", "Strikes Back!", 4.8));
        comicList.add(new Comic("Deadpool", "Bow to the king", 4.9));

        // Configurar el Adapter
        comicAdapter = new ComicAdapter(this, comicList);
        recyclerViewComics.setAdapter(comicAdapter);

        // Configurar el botón "Ver más" para hacer scroll al siguiente cómic
        btnSeeMore.setOnClickListener(v -> {
            int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
            recyclerViewComics.smoothScrollToPosition(firstVisibleItem + 1);
        });
    }

    // Método para abrir actividad de productos
    private void openProductActivity(String category) {
        Intent intent = new Intent(HomeActivity.this, ProductActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}