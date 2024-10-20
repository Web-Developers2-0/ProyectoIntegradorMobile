package com.example.planetsuperheroes;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.planetsuperheroes.models.UserCrudInfo;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class infoPersonal extends AppCompatActivity {

    private static final String BASE_URL = "https://planetsuperheroes.onrender.com/";
    private ApiService apiService;
    private String userId = "12345"; // Reemplaza con el ID real del usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_personal);

        // Configuración de padding para las vistas
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Crear la instancia de Retrofit y ApiService
        apiService = RetrofitClient.getClient(this).create(ApiService.class);

        // Obtener la información del usuario (GET)
        getUserInfo();
    }

    // Método para realizar la solicitud GET y obtener los datos del usuario
    private void getUserInfo() {
        Call<UserCrudInfo> call = apiService.getUserInfo(userId);
        call.enqueue(new Callback<UserCrudInfo>() {
            @Override
            public void onResponse(Call<UserCrudInfo> call, Response<UserCrudInfo> response) {
                if (response.isSuccessful()) {
                    UserCrudInfo user = response.body();
                    if (user != null) {
                        Log.d("InfoPersonal", "Nombre: " + user.getName());
                        Log.d("InfoPersonal", "Apellido: " + user.getLastName());
                        Log.d("InfoPersonal", "Email: " + user.getEmail());
                        Log.d("InfoPersonal", "Dirección: " + user.getAddress());
                    }
                } else {
                    Log.e("InfoPersonal", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserCrudInfo> call, Throwable t) {
                Log.e("InfoPersonal", "Error: " + t.getMessage());
            }
        });
    }

    // Método para realizar la solicitud PATCH y actualizar los datos del usuario
    private void updateUserInfo(UserCrudInfo updatedUser) {
        Call<Void> call = apiService.updateUserInfo(userId, updatedUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("InfoPersonal", "Datos actualizados correctamente");
                } else {
                    Log.e("InfoPersonal", "Error en la actualización: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("InfoPersonal", "Error: " + t.getMessage());
            }
        });
    }
}



