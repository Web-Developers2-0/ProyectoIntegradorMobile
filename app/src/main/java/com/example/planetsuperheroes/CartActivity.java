package com.example.planetsuperheroes;

import android.content.DialogInterface;
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
    private Button btnClearCart; // Nuevo botón para limpiar el carrito
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
        btnClearCart = findViewById(R.id.btnClearCart); // Inicializar el botón

        // Inicializar ApiService
        apiService = RetrofitClient.getClient(this).create(ApiService.class);

        // Configurar el RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerViewCartItems.setLayoutManager(layoutManager);

        // Obtener productos del carrito desde CartManager
        Map<Product, Integer> cartItems = CartManager.getInstance().getCartItems();

        // Crear la lista de OrderItem
        List<OrderItem> orderItems = new ArrayList<>();
        final double[] totalAmount = {0}; // Usar un arreglo para poder modificarlo dentro de los métodos anónimos

        // Recorrer el mapa de productos y cantidades
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue(); // Obtener la cantidad del producto
            orderItems.add(new OrderItem(product.getId(), quantity));
            totalAmount[0] += product.getPrice() * quantity; // Calcular el total
        }

        // Inicializar el adapter
        cartAdapter = new CartAdapter(this, orderItems);
        recyclerViewCartItems.setAdapter(cartAdapter);

        // Mostrar el monto total
        totalAmountText.setText("Total: $" + totalAmount[0]);

        // Obtener el ID del usuario
        getUserId();

        // Configurar el botón de checkout
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId != -1) { // Verificar que se haya obtenido un ID válido
                    createOrder(orderItems, totalAmount[0]); // Llamar al método para crear la orden
                } else {
                    Toast.makeText(CartActivity.this, "No se pudo obtener el ID del usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el botón de limpiar carrito
        btnClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClearCartConfirmationDialog(); // Mostrar diálogo de confirmación
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
        // Crear la lista de order_items
        List<Map<String, Object>> orderItemsList = new ArrayList<>();

        for (OrderItem item : orderItems) {
            Map<String, Object> orderItemMap = new HashMap<>();
            orderItemMap.put("product", item.getProduct());
            orderItemMap.put("quantity", item.getQuantity());
            orderItemsList.add(orderItemMap);
        }

        // Agregar la lista de order_items al mapa de orderData
        orderData.put("order_items", orderItemsList);

        // Llamar al método para crear la orden
        Call<Order> call = apiService.createOrder(orderData);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Order createdOrder = response.body();
                    Log.d("CartActivity", "Orden creada: " + createdOrder);
                    Toast.makeText(CartActivity.this, "Orden creada con éxito!", Toast.LENGTH_SHORT).show();

                    // Log antes de limpiar el carrito
                    Log.d("CartActivity", "Elementos en el carrito antes de limpiar: " + CartManager.getInstance().getCartItems());
                    Log.d("CartActivity", "Limpiando el carrito...");
                    CartManager.getInstance().clearCart(); // Limpiar el carrito

                    // Log después de limpiar el carrito
                    Log.d("CartActivity", "Carrito limpiado, elementos restantes: " + CartManager.getInstance().getCartItemCount());

                    // Actualiza la interfaz si es necesario
                    cartAdapter.updateCartItems(new ArrayList<>()); // Limpia el adaptador
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

    private void showClearCartConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Limpieza");
        builder.setMessage("¿Estás seguro de que deseas limpiar el carrito?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("CartActivity", "Limpiando el carrito...");
                CartManager.getInstance().clearCart(); // Limpiar el carrito
                cartAdapter.updateCartItems(new ArrayList<>()); // Limpia el adaptador
                totalAmountText.setText("Total: $0.00"); // Actualiza el total
                Toast.makeText(CartActivity.this, "Carrito limpiado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Cierra el diálogo
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show(); // Mostrar el diálogo
    }
}
