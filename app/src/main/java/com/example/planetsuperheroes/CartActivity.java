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
                    List<OrderItem> orderItems = cartAdapter.getOrderItems();
                    if (validateOrderItems(orderItems)) {
                        // Si paymentData es null, significa que se debe enviar al usuario a CheckoutActivity
                        if (paymentData == null) {
                            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                            startActivity(intent);
                        } else {
                            createOrder(orderItems, totalAmount[0], paymentData);
                        }
                    } else {
                        Toast.makeText(CartActivity.this, "Datos de la orden inválidos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CartActivity.this, "No se pudo obtener el ID del usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el botón de limpiar carrito
        btnClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClearCartConfirmationDialog();
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

    private void createOrder(List<OrderItem> orderItems, double totalAmount, Map<String, String> paymentData) {
        Map<String, Object> orderData = new HashMap<>();
        List<Map<String, Object>> orderItemsList = new ArrayList<>();

        for (OrderItem item : orderItems) {
            Map<String, Object> orderItemMap = new HashMap<>();
            orderItemMap.put("product", item.getProduct());
            orderItemMap.put("quantity", item.getQuantity());
            orderItemsList.add(orderItemMap);
        }

        orderData.put("order_items", orderItemsList);
        orderData.put("paymentForm", paymentData);

        Log.d("CartActivity", "Creando orden con los siguientes datos: " + orderData);

        Call<Order> call = apiService.createOrder(orderData);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Order createdOrder = response.body();
                    Log.d("CartActivity", "Orden creada: " + createdOrder);
                    Toast.makeText(CartActivity.this, "Orden creada con éxito!", Toast.LENGTH_SHORT).show();
                    CartManager.getInstance().clearCart();
                    cartAdapter.updateCartItems(new ArrayList<>());
                    totalAmountText.setText("Total: $0.00");
                } else {
                    Log.e("CartActivity", "Error al crear la orden: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.e("CartActivity", "Error en la llamada al crear orden: " + t.getMessage());
            }
        });
    }

    private boolean validateOrderItems(List<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            if (item.getProduct() <= 0 || item.getQuantity() <= 0) {
                Log.d("CartActivity", "Error: producto o cantidad inválidos en la orden.");
                return false;
            }
        }
        Log.d("CartActivity", "Todos los datos de la orden son válidos.");
        return true;
    }

    private void showClearCartConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Limpieza");
        builder.setMessage("¿Estás seguro de que deseas limpiar el carrito?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("CartActivity", "Limpiando el carrito...");
                CartManager.getInstance().clearCart();
                cartAdapter.updateCartItems(new ArrayList<>());
                totalAmountText.setText("Total: $0.00");
                Toast.makeText(CartActivity.this, "Carrito limpiado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
