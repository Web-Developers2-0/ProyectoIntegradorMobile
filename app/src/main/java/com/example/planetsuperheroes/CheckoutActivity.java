package com.example.planetsuperheroes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
                    showConfirmationDialog();
                }
            }
        });
    }

    // Método para seleccionar una tarjeta de pago
    private void selectCard(LinearLayout selectedCard) {
        // Restablecer el color de fondo de las tarjetas a su estado normal
        card1.setBackgroundResource(R.drawable.border_shape);
        card2.setBackgroundResource(R.drawable.border_shape);

        // Cambiar el color de fondo de la tarjeta seleccionada
        selectedCard.setBackgroundResource(R.drawable.card_selected_background);

        // Restablecer el color del texto de las otras tarjetas
        if (selectedCard == card1) {
            TextView paymentText2 = card2.findViewById(R.id.tvPaymentMethod2);
            paymentText2.setTextColor(getResources().getColor(R.color.text_primary_dark));
        } else {
            TextView paymentText1 = card1.findViewById(R.id.tvPaymentMethod1);
            paymentText1.setTextColor(getResources().getColor(R.color.text_primary_dark));
        }

        // Activar el botón de comprar
        isCardSelected = true;
        btnComprar.setEnabled(true);
    }

    // Mostrar diálogo de confirmación
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
        builder.setTitle("Confirmación de Compra");
        builder.setMessage("La compra ha sido procesada exitosamente. ¿Desea volver al inicio?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Redirigir al usuario a la pantalla de inicio (MainActivity o similar)
                Intent intent = new Intent(CheckoutActivity.this, MainActivity.class); // Reemplaza MainActivity con la actividad a la que quieras ir
                startActivity(intent);
                finish(); // Finalizar la actividad actual
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Cerrar el diálogo si el usuario cancela
            }
        });
        builder.show();
    }
}