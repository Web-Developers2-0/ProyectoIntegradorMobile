package com.example.planetsuperheroes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planetsuperheroes.models.User;
import com.example.planetsuperheroes.models.UserResponse;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {
    private Button buttonRegistrarse;
    private EditText etUserName, etEmail, etPassword, etFirstName, etLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Referencias a los elementos de la interfaz
        buttonRegistrarse = findViewById(R.id.btnRegister);
        etUserName = findViewById(R.id.etUserName);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        // Acción del botón para registrar al usuario
        buttonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUserName.getText().toString();
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || username.isEmpty()) {
                    Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Crear un objeto User con los datos ingresados
                    User user = new User(username,firstName,lastName,email,password);

                    // Llamada a la API utilizando RetrofitClient ya existente
                    ApiService apiService = RetrofitClient.getClient(getApplicationContext()).create(ApiService.class);
                    Call<UserResponse> call = apiService.registerUser(user);

                    // Encolar la solicitud
                    call.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(RegistroActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                if (response.body().isSuccess()) {
                                    // Redireccionar a la pantalla principal después del registro exitoso
                                    Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(RegistroActivity.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            // Manejo del error de conexión
                            Toast.makeText(RegistroActivity.this, "Fallo la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}