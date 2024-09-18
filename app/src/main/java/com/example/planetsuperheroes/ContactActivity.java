package com.example.planetsuperheroes;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextMessage;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String message = editTextMessage.getText().toString();
                String emailRegex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(email);

                // Valido los datos del formulario de contacto
                if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                    Toast.makeText(ContactActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                } else if (!matcher.matches()) {
                    Toast.makeText(ContactActivity.this, "Debe ser una dirección de email válida.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Envio los datos del formulario a nuestra API, esto se termina de ingregrar en el proximo sprint
                Toast.makeText(ContactActivity.this, "Mensaje enviado: " + name + ", " + email + ", " + message, Toast.LENGTH_SHORT).show();

                // Limpio los campos del formulario cuando se envia exitosamente el mensaje
                editTextName.setText("");
                editTextEmail.setText("");
                editTextMessage.setText("");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}