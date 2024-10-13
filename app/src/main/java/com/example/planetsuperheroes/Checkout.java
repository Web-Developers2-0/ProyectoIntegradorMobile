package com.example.planetsuperheroes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {

    private LinearLayout card1, card2;
    private Button btnComprar;
    private boolean isCardSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Referencias a los elementos del layout
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        btnComprar = findViewById(R.id.btnComprar);

        // Deshabilitar el botón de comprar inicialmente
        btnComprar.setEnabled(false);

        // Listener para seleccionar tarjeta 1
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCard(card1);
            }
        });

        // Listener para seleccionar tarjeta 2
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCard(card2);
            }
        });

        // Listener para el botón de comprar
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCardSelected) {
                    Toast.makeText(CheckoutActivity.this, "Compra realizada", Toast.LENGTH_SHORT).show();
                    // Aquí puedes agregar la lógica para enviar la información de la compra al backend
                }
            }
        });
    }

    // Método para seleccionar una tarjeta de pago
    private void selectCard(LinearLayout selectedCard) {
        // Restablecer el color de fondo de las tarjetas a su estado normal
        card1.setBackgroundResource(R.drawable.card_background);
        card2.setBackgroundResource(R.drawable.card_background);

        // Cambiar el color de fondo de la tarjeta seleccionada
        selectedCard.setBackgroundResource(R.drawable.card_selected_background);

        // Activar el botón de comprar
        isCardSelected = true;
        btnComprar.setEnabled(true);
    }
}
