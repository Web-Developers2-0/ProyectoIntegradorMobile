package com.example.planetsuperheroes;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView; // Asegúrate de importar ImageView
import android.widget.LinearLayout;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Settings extends AppCompatActivity {
    private ImageButton imageButtonProfile;
    private LinearLayout linearPreguntasFrecuentes;
    private ImageView flechaBotonPreguntas; // Añade esta línea para la flecha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        imageButtonProfile = findViewById(R.id.imageButtonProfile);
        linearPreguntasFrecuentes = findViewById(R.id.linearpreguntasfrecuentes); // Encuentra el LinearLayout
        flechaBotonPreguntas = findViewById(R.id.flechaBotonPreguntas); // Encuentra la flecha

        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, infoPersonal.class);
                startActivity(intent);
            }
        });

        // Agrega el OnClickListener para el LinearLayout de preguntas frecuentes
        linearPreguntasFrecuentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, FaqActivity.class);
                startActivity(intent);
            }
        });

        // Agrega el OnClickListener para la flecha
        flechaBotonPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, FaqActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Settings), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}