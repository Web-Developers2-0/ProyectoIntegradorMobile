package com.example.planetsuperheroes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planetsuperheroes.models.LoginRequest;
import com.example.planetsuperheroes.models.LoginResponse;
import com.example.planetsuperheroes.network.ApiService;

import com.example.planetsuperheroes.network.RetrofitClient;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;


public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonIniciarSesion;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializo vistas
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion);

        sharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);

        // Manejar el click del botón de inicio de sesión
        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Reemplazo la logica de credenciales hardcodeadas a la dinamica
                    LoginRequest loginRequest = new LoginRequest(email, password);

                    Retrofit retrofit = RetrofitClient.getClient(LoginActivity.this);
                    ApiService apiService = retrofit.create(ApiService.class);
                    Call<LoginResponse> call = apiService.loginUser(loginRequest);

                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                            // Armo caso de Login successful, navigate to HomeActivity
                            LoginResponse loginResponse = null;

                            if (response.isSuccessful()) {
                                loginResponse = response.body();
                                if (loginResponse != null) {
                                    String token = loginResponse.getToken();
                                    storeToken(token);

                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                // Armo caso de Login failed
                                    Toast.makeText(LoginActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Error de red o del servidor", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    // armar la logica de SharedPreferences
    private void storeToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);

        editor.apply();
    }
}