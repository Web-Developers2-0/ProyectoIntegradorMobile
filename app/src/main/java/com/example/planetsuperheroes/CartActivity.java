package com.example.planetsuperheroes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planetsuperheroes.models.Order;
import com.example.planetsuperheroes.models.OrderItem;
import com.example.planetsuperheroes.models.Product;
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

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCartItems;
    private CartAdapter cartAdapter;
    private LinearLayoutManager layoutManager;
    private TextView totalAmountText;
    private Button btnCheckout;
    private ApiService apiService;
    private int userId; // Variable para almacenar el ID del usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Inicializar vistas
        recyclerViewCartItems = findViewById(R.id.recyclerViewCartItems);
        totalAmountText = findViewById(R.id.totalAmountText);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Inicializar ApiService
        apiService = RetrofitClient.getClient(this).create(ApiService.class);

        // Configurar el RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerViewCartItems.setLayoutManager(layoutManager);

        // Obtener productos del carrito desde CartManager
        List<Product> cartProducts = CartManager.getInstance().getCartItems();

        // Crear la lista de OrderItem
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (Product product : cartProducts) {
            int quantity = 1; // Cambia esto si tienes otra lógica para la cantidad
            orderItems.add(new OrderItem(product.getId(), quantity));
            totalAmount += product.getPrice() * quantity; // Asegúrate de que 'getPrice()' devuelve el precio del producto
        }

        // Inicializar el adapter
        cartAdapter = new CartAdapter(this, orderItems);
        recyclerViewCartItems.setAdapter(cartAdapter);

        // Mostrar el monto total
        totalAmountText.setText("Total: $" + totalAmount);

        // Obtener el ID del usuario
        getUserId();

        // Configurar el botón de checkout
        double finalTotalAmount = totalAmount;
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId != -1) { // Verificar que se haya obtenido un ID válido
                    createOrder(orderItems, finalTotalAmount); // Llamar al método para crear la orden
                } else {
                    Toast.makeText(CartActivity.this, "No se pudo obtener el ID del usuario", Toast.LENGTH_SHORT).show();
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
                    userId = user.getId(); // Asignar el ID del usuario
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

    private void createOrder(List<OrderItem> orderItems, double totalAmount) {
        // Crear un nuevo mapa para la solicitud
        Map<String, Object> orderData = new HashMap<>();
        //orderData.put("state", "pending");
        //orderData.put("payment_method", "credit_card");
        //orderData.put("shipping_method", "standard");
        //orderData.put("payment_status", "pagado");
        //orderData.put("total_amount", totalAmount);

        // Crear la lista de order_items
        List<Map<String, Object>> orderItemsList = new ArrayList<>();
        for (OrderItem item : orderItems) {
            Map<String, Object> orderItemMap = new HashMap<>();
            orderItemMap.put("product", item.getProduct()); // Aquí asegúrate de que getProduct() retorne el ID del producto
            orderItemMap.put("quantity", item.getQuantity());
            orderItemsList.add(orderItemMap);
        }

        // Agregar la lista de order_items al mapa de orderData
        orderData.put("order_items", orderItemsList);

        // Llamar al método para crear la orden
        Call<Order> call = apiService.createOrder(orderData); // Asegúrate de que este método esté definido en ApiService
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Order createdOrder = response.body();
                    // Manejar la respuesta exitosa (orden creada)
                    Log.d("CartActivity", "Orden creada: " + createdOrder);
                    Toast.makeText(CartActivity.this, "Orden creada con éxito!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("CartActivity", "Error al crear la orden: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.e("CartActivity", "Error en la llamada al crear orden: " + t.getMessage());
            }
        });
    }
}