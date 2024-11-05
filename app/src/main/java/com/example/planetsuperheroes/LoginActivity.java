package com.example.planetsuperheroes;

import android.content.SharedPreferences;
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

    private static final int MAX_ATTEMPTS = 4; // Cambiado a 4 intentos
    private static final long LOCKOUT_DURATION_MS = 1 * 60 * 1000; // 1 minuto en milisegundos
    private int failedAttempts = 0;
    private long lockoutEndTime = 0;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "LoginPrefs";
    private static final String KEY_FAILED_ATTEMPTS = "failedAttempts";
    private static final String KEY_LOCKOUT_END_TIME = "lockoutEndTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializo SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        failedAttempts = sharedPreferences.getInt(KEY_FAILED_ATTEMPTS, 0);
        lockoutEndTime = sharedPreferences.getLong(KEY_LOCKOUT_END_TIME, 0);

        // Inicializo vistas
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion);
        ivShowHidePassword = findViewById(R.id.ivShowHidePassword);

        // Verificar si la cuenta está bloqueada
        checkLockout();

        // Manejar el click del botón de inicio de sesión
        buttonIniciarSesion.setOnClickListener(v -> loginUser());

        // Manejar el ocultar/mostrar password
        ivShowHidePassword.setOnClickListener(v -> togglePasswordVisibility(editTextPassword, ivShowHidePassword, true));
    }

    private void loginUser() {
        // Verificar si la cuenta está bloqueada antes de intentar el inicio de sesión
        if (System.currentTimeMillis() < lockoutEndTime) {
            long remainingTime = (lockoutEndTime - System.currentTimeMillis()) / 1000;
            Toast.makeText(this, "Cuenta bloqueada. Inténtalo en " + remainingTime / 60 + " minutos y " + remainingTime % 60 + " segundos.", Toast.LENGTH_SHORT).show();
            return;
        }

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
            Toast.makeText(this, "La contraseña debe contener al menos 8 caracteres, una mayúscula, una minúscula, un carácter especial y un número.", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(email, password);
        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        Call<LoginResponse> call = apiService.loginUser(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Login exitoso, reiniciar los intentos fallidos y desbloquear cuenta
                    failedAttempts = 0;
                    lockoutEndTime = 0;
                    updatePreferences();

                    String token = response.body().getToken();
                    RetrofitClient.setToken(token);
                    Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Login fallido, incrementar intentos fallidos solo si no se ha alcanzado el límite
                    failedAttempts++;
                    if (failedAttempts >= MAX_ATTEMPTS) {
                        lockoutEndTime = System.currentTimeMillis() + LOCKOUT_DURATION_MS;
                        Toast.makeText(LoginActivity.this, "Cuenta bloqueada. Inténtalo en 1 minuto.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Credenciales incorrectas. Intento " + failedAttempts + " de " + MAX_ATTEMPTS, Toast.LENGTH_SHORT).show();
                    }
                    updatePreferences();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_FAILED_ATTEMPTS, failedAttempts);
        editor.putLong(KEY_LOCKOUT_END_TIME, lockoutEndTime);
        editor.apply();
    }

    private void checkLockout() {
        if (System.currentTimeMillis() < lockoutEndTime) {
            long remainingTime = (lockoutEndTime - System.currentTimeMillis()) / 1000;
            Toast.makeText(this, "Cuenta bloqueada. Inténtalo en " + remainingTime / 60 + " minutos y " + remainingTime % 60 + " segundos.", Toast.LENGTH_LONG).show();
        }
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
                password.matches(".*[@#$%^&+=!_.-].*");
    }
}
