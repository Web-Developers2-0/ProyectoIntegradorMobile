package com.example.planetsuperheroes;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Habilitar pantalla completa (Edge-to-Edge)
        View mainView = findViewById(R.id.checkout_title);

        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            // Obtener las barras del sistema (status bar y navigation bar)
            WindowInsetsCompat insetsCompat = insets;
            v.setPadding(
                    insetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insetsCompat;
        });

        // Ocultar las barras de sistema si es necesario
        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(mainView);
        if (windowInsetsController != null) {
            windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        }

        // Configurar el botón de compra
        findViewById(R.id.buy_button).setOnClickListener(v -> {
            if (validateInputs()) {
                // Procesar la compra
                Toast.makeText(this, "Compra exitosa", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Función para validar las entradas de los campos
    private boolean validateInputs() {
        String cardName = ((EditText) findViewById(R.id.card_name)).getText().toString().trim();
        String cardNumber = ((EditText) findViewById(R.id.card_number_input)).getText().toString().trim();
        String expiryDate = ((EditText) findViewById(R.id.expiry_date_input)).getText().toString().trim();
        String cvv = ((EditText) findViewById(R.id.cvv_input)).getText().toString().trim();

        // Validar Nombre de la Tarjeta
        if (TextUtils.isEmpty(cardName)) {
            ((EditText) findViewById(R.id.card_name)).setError("Ingrese el nombre de la tarjeta");
            return false;
        }

        // Validar número de tarjeta
        if (TextUtils.isEmpty(cardNumber) || cardNumber.length() != 16) {
            ((EditText) findViewById(R.id.card_number_input)).setError("Ingrese un número de tarjeta válido (16 dígitos)");
            return false;
        }

        // Validar fecha de expiración
        if (TextUtils.isEmpty(expiryDate) || !expiryDate.matches("\\d{2}/\\d{2}")) {
            ((EditText) findViewById(R.id.expiry_date_input)).setError("Ingrese una fecha válida (MM/AA)");
            return false;
        }

        // Validar CVV
        if (TextUtils.isEmpty(cvv) || (cvv.length() != 3 && cvv.length() != 4)) {
            ((EditText) findViewById(R.id.cvv_input)).setError("Ingrese un CVV válido (3 o 4 dígitos)");
            return false;
        }

        return true; // Si todas las validaciones son correctas
    }
}
