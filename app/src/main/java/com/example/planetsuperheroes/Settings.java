package com.example.planetsuperheroes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.planetsuperheroes.models.LogoutResponse;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.models.User;
import com.example.planetsuperheroes.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings extends AppCompatActivity {
    private ImageButton imageButtonProfile;
    private Button logoutButton;
    private ImageButton flechaBotonPreguntas;
    private ImageButton flechaBotonContactanos;
    private ApiService apiService;
    private TextView userName;
    private TextView userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        imageButtonProfile = findViewById(R.id.imageButtonProfile);
        logoutButton = findViewById(R.id.logoutButton);
        flechaBotonPreguntas = findViewById(R.id.flechaBotonPreguntas);
        flechaBotonContactanos = findViewById(R.id.flechaBotonContactanos);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        apiService = RetrofitClient.getClient(this).create(ApiService.class);

        getUserInfo();

        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, infoPersonal.class);
                startActivity(intent);
            }
        });

        flechaBotonPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, FaqActivity.class);
                startActivity(intent);
            }
        });

        flechaBotonContactanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(v -> logoutUser());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Settings), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void logoutUser() {
        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        Call<LogoutResponse> call = apiService.logoutUser();

        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(Settings.this, "Cierre de sesión exitoso", Toast.LENGTH_SHORT).show();
                    RetrofitClient.setToken(null);

                    Intent intent = new Intent(Settings.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Settings.this, "Error al intentar cerrar sesión", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(Settings.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
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
                        String email = user.getEmail();

                        userName.setText( username );
                        userEmail.setText( email );
                    }
                } else {
                    Log.e("SettingsActivity", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("SettingsActivity", "Error: " + t.getMessage());
            }
        });
    }
}