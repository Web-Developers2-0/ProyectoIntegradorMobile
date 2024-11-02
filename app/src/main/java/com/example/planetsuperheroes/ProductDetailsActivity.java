package com.example.planetsuperheroes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productNameTextView, productDescriptionTextView, productPriceTextView;
    private ImageView backArrow; // Referencia a la flecha de retroceso

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Inicializa las vistas
        productImageView = findViewById(R.id.productImageView);
        productNameTextView = findViewById(R.id.productNameTextView);
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        backArrow = findViewById(R.id.backArrow); // Inicializa la referencia a la flecha de retroceso

        // Asegúrate de que el ImageView sea clickeable
        backArrow.setClickable(true);

        // Obtenemos los datos del intent
        String productName = getIntent().getStringExtra("productName");
        String productDescription = getIntent().getStringExtra("productDescription");
        double productPrice = getIntent().getDoubleExtra("productPrice", 0.0);
        String productImage = getIntent().getStringExtra("productImage");

        // Seteamos los datos en la UI
        productNameTextView.setText(productName);
        productDescriptionTextView.setText(productDescription);
        productPriceTextView.setText("$" + productPrice);

        // Cargar la imagen
        if (productImage != null) {
            Glide.with(this).load(productImage).into(productImageView);
        } else {
            productImageView.setImageDrawable(null);
        }

        // Configura el listener para el botón de retroceso
        backArrow.setOnClickListener(v -> {
            Log.d("ProductDetailsActivity", "Back arrow clicked"); // Agrega un log para verificar el clic
            finish(); // Termina la actividad actual y regresa a la anterior
        });
    }
}
