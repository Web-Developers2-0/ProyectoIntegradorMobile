package com.example.planetsuperheroes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.planetsuperheroes.models.User;
import com.example.planetsuperheroes.models.UserResponse;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText etEmail, etConfirmEmail, etPassword, etConfirmPassword, etUserName;
    private ImageView ivShowHidePassword, ivShowHideConfirmPassword, ivPasswordInfo;
    private Button btnRegister;
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Referencias a los elementos de la interfaz
        etEmail = findViewById(R.id.etEmail);
        etConfirmEmail = findViewById(R.id.etConfirmEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etUserName = findViewById(R.id.etUserName);
        ivShowHidePassword = findViewById(R.id.ivShowHidePassword);
        ivShowHideConfirmPassword = findViewById(R.id.ivShowHideConfirmPassword);
        ivPasswordInfo = findViewById(R.id.ivPasswordInfo);
        btnRegister = findViewById(R.id.btnRegister);

        // Configuración del botón de información de contraseña
        ivPasswordInfo.setOnClickListener(v -> showPasswordRequirements());

        // Configuración de visibilidad de contraseña
        ivShowHidePassword.setOnClickListener(v -> togglePasswordVisibility(etPassword, ivShowHidePassword, true));
        ivShowHideConfirmPassword.setOnClickListener(v -> togglePasswordVisibility(etConfirmPassword, ivShowHideConfirmPassword, false));

        // Acción del botón para registrar al usuario
        btnRegister.setOnClickListener(v -> registerUser());

        // Verificación de coincidencia de email y contraseña
        etConfirmEmail.addTextChangedListener(new ValidationTextWatcher());
        etConfirmPassword.addTextChangedListener(new ValidationTextWatcher());
    }

    private void showPasswordRequirements() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Requisitos de la contraseña");
        builder.setMessage("La contraseña debe tener al menos 8 caracteres, incluyendo una mayúscula, una minúscula, un número y un carácter especial.");
        builder.setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void togglePasswordVisibility(EditText editText, ImageView icon, boolean isPassword) {
        if (isPassword) {
            isPasswordVisible = !isPasswordVisible;
            icon.setImageResource(isPasswordVisible ? R.drawable.ic_eye_on : R.drawable.ic_eye_off);
        } else {
            isConfirmPasswordVisible = !isConfirmPasswordVisible;
            icon.setImageResource(isConfirmPasswordVisible ? R.drawable.ic_eye_on : R.drawable.ic_eye_off);
        }
        editText.setInputType(isPasswordVisible || isConfirmPasswordVisible ?
                android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText.setSelection(editText.getText().length());
    }

    private void registerUser() {
        String email = etEmail.getText().toString();
        String confirmEmail = etConfirmEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String username = etUserName.getText().toString();

        if (email.isEmpty() || confirmEmail.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.equals(confirmEmail)) {
            Toast.makeText(this, "Los correos electrónicos no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Por favor, ingresa un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "La contraseña no cumple con los requisitos", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(username, email, password);
        ApiService apiService = RetrofitClient.getClient(getApplicationContext()).create(ApiService.class);
        Call<UserResponse> call = apiService.registerUser(user);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                    finish();
                } else {
                    // Manejo de error para nombre de usuario duplicado
                    if (response.code() == 400) {
                        try {
                            String errorBody = response.errorBody().string();
                            if (errorBody.contains("username") && errorBody.contains("already exists")) {
                                Toast.makeText(RegistroActivity.this, "El nombre de usuario ya está en uso. Prueba con otro.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegistroActivity.this, "Error: Datos inválidos", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(RegistroActivity.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegistroActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(RegistroActivity.this, "Fallo la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    private class ValidationTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!etEmail.getText().toString().equals(etConfirmEmail.getText().toString())) {
                etConfirmEmail.setError("Los correos no coinciden");
            }

            if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                etConfirmPassword.setError("Las contraseñas no coinciden");
            }
        }

        @Override
        public void afterTextChanged(Editable s) { }
    }
}
