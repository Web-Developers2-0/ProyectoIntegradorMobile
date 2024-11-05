package com.example.planetsuperheroes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.planetsuperheroes.models.UserCrudInfo;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.network.RetrofitClient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextMessage;
    private Button buttonSend;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        // Inicializar ApiService
        apiService = RetrofitClient.getClient(this).create(ApiService.class);

        // Llamar al método para obtener la información del usuario
        getUserInfo();


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String message = editTextMessage.getText().toString();
                String emailRegex = "^[\\w!#$%&'+/=?{|}~^-]+(?:\\.[\\w!#$%&'+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(email);

                // Validar los datos del formulario de contacto
                if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                    Toast.makeText(ContactActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!matcher.matches()) {
                    Toast.makeText(ContactActivity.this, "Debe ser una dirección de email válida.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Enviar el mensaje a través de un Intent de correo
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:")); // Solo aplicaciones de correo
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"developers-superheroes@yopmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Mensaje de Contacto de " + name);
                emailIntent.putExtra(Intent.EXTRA_TEXT, message);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar correo..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ContactActivity.this, "No hay clientes de correo instalados.", Toast.LENGTH_SHORT).show();
                }

                editTextMessage.setText("");
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método para obtener la información del usuario
    private void getUserInfo() {
        Call<UserCrudInfo> call = apiService.getUserCrudInfo(); // El interceptor añadirá el token automáticamente
        call.enqueue(new Callback<UserCrudInfo>() {
            @Override
            public void onResponse(Call<UserCrudInfo> call, Response<UserCrudInfo> response) {
                if (response.isSuccessful()) {
                    UserCrudInfo user = response.body();
                    if (user != null) {
                        editTextName.setText(user.getName());
                        editTextEmail.setText(user.getEmail());
                    }
                } else {
                    Toast.makeText(ContactActivity.this, "Error en la respuesta: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserCrudInfo> call, Throwable t) {
                Toast.makeText(ContactActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}