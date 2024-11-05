package com.example.planetsuperheroes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetsuperheroes.models.UserCrudInfo;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class infoPersonal extends AppCompatActivity {

    private ApiService apiService;
    private EditText nameEditText, lastNameEditText, emailEditText, addressEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_personal);

        // Inicializar vistas
        nameEditText = findViewById(R.id.editTextName);
        lastNameEditText = findViewById(R.id.editTextlastName);
        emailEditText = findViewById(R.id.editTextEmail);
        addressEditText = findViewById(R.id.editTextAdress);
        saveButton = findViewById(R.id.btnGuardarCambios);

        // Inicializar ApiService
        apiService = RetrofitClient.getClient(this).create(ApiService.class);

        // Llamar al método para obtener la información del usuario
        getUserInfo();

        // Configurar el botón para actualizar los datos del usuario
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    UserCrudInfo updatedUser = new UserCrudInfo(
                            0, // ID no es necesario para la actualización
                            emailEditText.getText().toString(),
                            nameEditText.getText().toString(),
                            lastNameEditText.getText().toString(),
                            addressEditText.getText().toString(),
                            null, // No necesitamos el phone aquí
                            null, // Password no se necesita
                            null  // ConfirmPassword tampoco
                    );

                    // Llamar al método para actualizar la información del usuario
                    updateUserInfo(updatedUser);
                }
            }
        });
    }

    // Método para obtener la información del usuario
    private void getUserInfo() {
        Call<UserCrudInfo> call = apiService.getUserCrudInfo();  // El interceptor añadirá el token automáticamente
        call.enqueue(new Callback<UserCrudInfo>() {
            @Override
            public void onResponse(Call<UserCrudInfo> call, Response<UserCrudInfo> response) {
                if (response.isSuccessful()) {
                    UserCrudInfo user = response.body();
                    if (user != null) {
                        nameEditText.setText(user.getName());
                        lastNameEditText.setText(user.getLastName());
                        emailEditText.setText(user.getEmail());
                        addressEditText.setText(user.getAddress());
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

    // Método para actualizar la información del usuario
    private void updateUserInfo(UserCrudInfo updatedUser) {
        Call<Void> call = apiService.updateUserCrudInfo(updatedUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(infoPersonal.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
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

    // Método para validar los campos
    private boolean validateFields() {
        // Definir las restricciones de longitud
        int minLength = 3;
        int maxLength = 30;

        // Obtener el texto de cada campo EditText
        String nombre = nameEditText.getText().toString();
        String apellido = lastNameEditText.getText().toString();
        String correo = emailEditText.getText().toString();
        String direccion = addressEditText.getText().toString();

        // Validar cada campo con las restricciones de longitud y condiciones específicas
        if (nombre.isEmpty() || nombre.length() < minLength || nombre.length() > maxLength) {
            Toast.makeText(infoPersonal.this, "El nombre debe tener entre " + minLength + " y " + maxLength + " caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (apellido.isEmpty() || apellido.length() < minLength || apellido.length() > maxLength) {
            Toast.makeText(infoPersonal.this, "El apellido debe tener entre " + minLength + " y " + maxLength + " caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (correo.isEmpty() || !correo.contains("@") || correo.length() > maxLength) {
            Toast.makeText(infoPersonal.this, "El correo debe contener '@' y no superar " + maxLength + " caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (direccion.isEmpty() || direccion.length() < minLength || direccion.length() > maxLength) {
            Toast.makeText(infoPersonal.this, "La dirección debe tener entre " + minLength + " y " + maxLength + " caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Verificar que todos los campos estén completos
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(infoPersonal.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Si todas las validaciones pasan
        return true;
    }
}

