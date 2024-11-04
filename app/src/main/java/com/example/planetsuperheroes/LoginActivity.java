package com.example.planetsuperheroes;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planetsuperheroes.models.LoginRequest;
import com.example.planetsuperheroes.models.LoginResponse;
import com.example.planetsuperheroes.network.ApiService;

import com.example.planetsuperheroes.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonIniciarSesion;
    private ImageView ivShowHidePassword;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializo vistas
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion);
        ivShowHidePassword = findViewById(R.id.ivShowHidePassword);

        // Manejar el click del botón de inicio de sesión
        buttonIniciarSesion.setOnClickListener(v -> loginUser());
        // Manejar el ocultar/mostrar password
        ivShowHidePassword.setOnClickListener(v -> togglePasswordVisibility(editTextPassword, ivShowHidePassword, true));
    }


    private void loginUser(){
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Debes ingresar un correo electrónico válido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidPassword(password)) {
                Toast.makeText(this, "La contraseña debe contener al menos 8 caracteres, una mayúscula, una minúscula, un carácter especial y un numero.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Reemplazo la logica de credenciales hardcodeadas a la dinamica
                LoginRequest loginRequest = new LoginRequest(email, password);
                ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
                Call<LoginResponse> call = apiService.loginUser(loginRequest);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        // Armo caso de Login successful, navigate to HomeActivity
                        if (response.isSuccessful() && response.body() != null) {
                            String token = response.body().getToken();
                            RetrofitClient.setToken(token);
                            Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                            } else {
                            // Armo caso de Login failed
                                Toast.makeText(LoginActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void togglePasswordVisibility(EditText editText, ImageView icon, boolean isPassword) {
        if (isPassword) {
            isPasswordVisible = !isPasswordVisible;
            icon.setImageResource(isPasswordVisible ? R.drawable.ic_eye_on : R.drawable.ic_eye_off);
        } else {
            isPasswordVisible = false;
            icon.setImageResource(R.drawable.ic_eye_off);
        }
        editText.setInputType(isPasswordVisible ?
                android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText.setSelection(editText.getText().length());
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[@#$%^&+=!].*");
    }
}