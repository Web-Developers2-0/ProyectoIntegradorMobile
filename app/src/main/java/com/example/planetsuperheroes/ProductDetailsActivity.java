package com.example.planetsuperheroes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productNameTextView, productDescriptionTextView, productPriceTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Referencias a los elementos de la UI
        productImageView = findViewById(R.id.productImageView);
        productNameTextView = findViewById(R.id.productNameTextView);
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);

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
        int imageResource = getResources().getIdentifier(productImage, "drawable", getPackageName());
        if (imageResource != 0) {
            Glide.with(this).load(imageResource).into(productImageView);
        } else {
            productImageView.setImageDrawable(null);  // Imagen por defecto si no se encuentra la imagen
        }
    }
}
