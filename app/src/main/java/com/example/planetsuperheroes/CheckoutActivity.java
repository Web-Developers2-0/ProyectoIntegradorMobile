package com.example.planetsuperheroes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetsuperheroes.models.Order;
import com.example.planetsuperheroes.models.OrderItem;
import com.example.planetsuperheroes.models.UserCrudInfo;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.network.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    private EditText editTextCardName, editTextCardNumber, editTextExpiryDate, editTextCVV;
    private Button btnConfirmPayment;
    private ApiService apiService;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Inicializar vistas
        editTextCardName = findViewById(R.id.card_name); // ID actualizado
        editTextCardNumber = findViewById(R.id.card_number_input); // ID actualizado
        editTextExpiryDate = findViewById(R.id.expiry_date_input); // ID actualizado
        editTextCVV = findViewById(R.id.cvv_input); // ID actualizado
        btnConfirmPayment = findViewById(R.id.buy_button); // ID actualizado

        // Inicializar ApiService
        apiService = RetrofitClient.getClient(this).create(ApiService.class);

        // Obtener el ID del usuario
        getUserId();

        // Configurar el botón de confirmar pago
        btnConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId != -1) {
                    Map<String, String> paymentData = validatePaymentData();
                    if (paymentData != null) {
                        List<OrderItem> orderItems = CartManager.getInstance().getOrderItems(); // Asumiendo que tienes este método
                        createOrder(orderItems, paymentData);
                    }
                } else {
                    Toast.makeText(CheckoutActivity.this, "No se pudo obtener el ID del usuario", Toast.LENGTH_SHORT).show();
                }
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
                    Log.d("CheckoutActivity", "ID del usuario obtenido: " + userId);
                } else {
                    Log.e("CheckoutActivity", "Error al obtener la información del usuario: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserCrudInfo> call, Throwable t) {
                Log.e("CheckoutActivity", "Error en la llamada: " + t.getMessage());
            }
        });
    }

    private Map<String, String> validatePaymentData() {
        String cardName = editTextCardName.getText().toString().trim(); // Agregado
        String cardNumber = editTextCardNumber.getText().toString().trim();
        String expiryDate = editTextExpiryDate.getText().toString().trim();
        String cvv = editTextCVV.getText().toString().trim();

        if (cardName.isEmpty() || cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return null;
        }

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("cardName", cardName); // Agregado
        paymentData.put("cardNumber", cardNumber);
        paymentData.put("expiryDate", expiryDate);
        paymentData.put("cvv", cvv);
        return paymentData;
    }

    private void createOrder(List<OrderItem> orderItems, Map<String, String> paymentData) {
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

        Log.d("CheckoutActivity", "Creando orden con los siguientes datos: " + orderData);

        Call<Order> call = apiService.createOrder(orderData);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Order createdOrder = response.body();
                    Log.d("CheckoutActivity", "Orden creada: " + createdOrder);
                    Toast.makeText(CheckoutActivity.this, "Orden creada con éxito!", Toast.LENGTH_SHORT).show();
                    CartManager.getInstance().clearCart(); // Limpiar el carrito
                    finish(); // Cerrar la actividad actual
                } else {
                    Log.e("CheckoutActivity", "Error al crear la orden: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.e("CheckoutActivity", "Error en la llamada al crear orden: " + t.getMessage());
            }
        });
    }
}
