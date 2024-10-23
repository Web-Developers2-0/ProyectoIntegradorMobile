package com.example.planetsuperheroes;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Settings extends AppCompatActivity {
    private ImageButton imageButtonProfile;
    private ImageButton flechaBotonHistorialCompra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        imageButtonProfile = findViewById(R.id.imageButtonProfile);
        flechaBotonHistorialCompra = findViewById(R.id.flechaBotonHistorialCompra);
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, infoPersonal.class);
                startActivity(intent);
            }
        });
        flechaBotonHistorialCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, Historial_Compras.class);
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