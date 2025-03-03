package com.example.planetsuperheroes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planetsuperheroes.models.Order;
import com.example.planetsuperheroes.models.OrderItem;
import com.example.planetsuperheroes.models.Product;
import com.example.planetsuperheroes.models.UserCrudInfo;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private static final int CHECKOUT_REQUEST_CODE = 1; // Código de solicitud para el checkout
    private RecyclerView recyclerViewCartItems;
    private CartAdapter cartAdapter;
    private LinearLayoutManager layoutManager;
    private TextView totalAmountText;
    private Button btnCheckout;
    private Button btnClearCart;
    private ApiService apiService;
    private int userId;

    // Variable para almacenar los datos de pago
    private Map<String, String> paymentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Inicializar vistas
        recyclerViewCartItems = findViewById(R.id.recyclerViewCartItems);
        totalAmountText = findViewById(R.id.totalAmountText);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnClearCart = findViewById(R.id.btnClearCart);

        // Inicializar ApiService
        apiService = RetrofitClient.getClient(this).create(ApiService.class);

        // Configurar el RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerViewCartItems.setLayoutManager(layoutManager);

        // Obtener productos del carrito desde CartManager
        Map<Product, Integer> cartItems = CartManager.getInstance().getCartItems();

        // Crear la lista de OrderItem
        List<OrderItem> orderItems = new ArrayList<>();
        final double[] totalAmount = {0};

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            Log.d("CartActivity", "ID del producto: " + product.getId() + ", Cantidad: " + quantity);
            orderItems.add(new OrderItem(product.getId(), product.getName(), quantity));
            totalAmount[0] += product.getPrice() * quantity;
        }

        // Inicializar el adapter
        cartAdapter = new CartAdapter(this, orderItems);
        recyclerViewCartItems.setAdapter(cartAdapter);

        // Mostrar el monto total
        totalAmountText.setText("Total: $" + totalAmount[0]);

        // Obtener el ID del usuario
        getUserId();

        // Recibir datos de pago desde el Intent, si están disponibles
        if (getIntent().hasExtra("paymentData")) {
            String paymentDataJson = getIntent().getStringExtra("paymentData");
            paymentData = new Gson().fromJson(paymentDataJson, new TypeToken<Map<String, String>>(){}.getType());
        }

        // Configurar el botón de checkout
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId != -1) {
                    // Validar si hay productos en el carrito
                    if (!orderItems.isEmpty()) {
                        Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                        startActivityForResult(intent, CHECKOUT_REQUEST_CODE);
                    } else {
                        Toast.makeText(CartActivity.this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CartActivity.this, "No se pudo obtener el ID del usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el botón para limpiar el carrito
        btnClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CartActivity.this)
                        .setTitle("Confirmar")
                        .setMessage("¿Está seguro de que desea limpiar el carrito?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                CartManager.getInstance().clearCart(); // Limpiar el carrito
                                cartAdapter.updateCartItems(new ArrayList<>()); // Actualizar la vista del carrito
                                totalAmountText.setText("Total: $0.00"); // Reiniciar el total
                                Toast.makeText(CartActivity.this, "El carrito ha sido limpiado", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    private void getUserId() {
        Call<UserCrudInfo> call = apiService.getUserCrudInfo();
        call.enqueue(new Callback<UserCrudInfo>() {
            @Override
            public void onResponse(Call<UserCrudInfo> call, Response<UserCrudInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserCrudInfo user = response.body();
                    userId = user.getId();
                    Log.d("CartActivity", "ID del usuario obtenido: " + userId);
                } else {
                    Log.e("CartActivity", "Error al obtener la información del usuario: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserCrudInfo> call, Throwable t) {
                Log.e("CartActivity", "Error en la llamada: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHECKOUT_REQUEST_CODE && resultCode == RESULT_OK) {
            boolean orderCreated = data.getBooleanExtra("orderCreated", false);
            if (orderCreated) {
                CartManager.getInstance().clearCart(); // Limpiar el carrito
                cartAdapter.updateCartItems(new ArrayList<>()); // Actualizar la vista del carrito
                totalAmountText.setText("Total: $0.00"); // Reiniciar el total
                Toast.makeText(this, "El carrito se ha limpiado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
