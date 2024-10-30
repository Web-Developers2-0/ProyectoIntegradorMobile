package com.example.planetsuperheroes;

import static com.example.planetsuperheroes.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planetsuperheroes.models.ProductActivity;
import com.example.planetsuperheroes.models.User;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ImageButton btnUser;
    private ImageButton btnNotification;
    private RecyclerView recyclerViewComics;
    private ComicAdapter comicAdapter;
    private LinearLayoutManager layoutManager;
    private ApiService apiService;
    private TextView textViewTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Inicializar vistas
        btnUser = findViewById(R.id.btnUser);
        btnNotification = findViewById(R.id.btnNotification);
        RecyclerView recyclerViewComics = findViewById(R.id.recyclerViewComics);
        Button btnSeeMore = findViewById(R.id.btnSeeMore);
        textViewTitle = findViewById(R.id.textViewTitle);
        apiService = RetrofitClient.getClient(this).create(ApiService.class);

        getUserInfo();

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

        // Crear lista de cómics hardcodeados para el carrusel
        List<Comic> comicList = new ArrayList<>();
        comicList.add(new Comic("Batman y Robin", "Hunters or Hunted?", 4.9, drawable.batman_robin));
        comicList.add(new Comic("Gwenpool", "Strikes Back!", 4.8, R.drawable.gwenpool));
        comicList.add(new Comic("Deadpool", "Bow to the king", 4.9, drawable.marveldeadpool));

        // Configurar el Adapter
        comicAdapter = new ComicAdapter(this, comicList);
        recyclerViewComics.setAdapter(comicAdapter);

        // Configurar el botón "Ver más" para hacer scroll al siguiente cómic
        btnSeeMore.setOnClickListener(v -> {
            int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
            recyclerViewComics.smoothScrollToPosition(firstVisibleItem + 1);
        });

        // Configuración del botón del carrito (btnCart)
        ImageButton btnCart = findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace(); // Imprime la traza de la excepción
                    Toast.makeText(HomeActivity.this, "Error al abrir el carrito: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    // Método para abrir actividad de productos
    private void openProductActivity(String category) {
        Intent intent = new Intent(HomeActivity.this, ProductActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    private void getUserInfo() {
        Call<User> call = apiService.getUserInfo();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        String username = user.getUsername();

                        textViewTitle.setText("Hola, " + username + "!");
                    }
                } else {
                    Log.e("HomeActivity", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("HomeActivity", "Error: " + t.getMessage());
            }
        });
    }
}