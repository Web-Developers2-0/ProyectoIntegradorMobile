package com.example.planetsuperheroes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private ImageButton btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        btnUser = findViewById(R.id.btnUser);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, infoPersonal.class);
                startActivity(intent);
            }
        });

        // Configura el listener para el botón de Marvel
        ImageButton btnMarvel = findViewById(R.id.btnMarvel);
        btnMarvel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductActivity("Marvel");
            }
        });

        // Configura el listener para el botón de DC
        ImageButton btnDC = findViewById(R.id.btnDC);
        btnDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductActivity("DC");
            }
        });
    }

    private void openProductActivity(String category) {
        Intent intent = new Intent(HomeActivity.this, ProductActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}