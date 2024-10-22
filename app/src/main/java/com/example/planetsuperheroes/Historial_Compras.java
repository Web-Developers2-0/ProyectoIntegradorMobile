package com.example.planetsuperheroes;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.planetsuperheroes.adapters.OrderAdapter;
import com.example.planetsuperheroes.network.ApiClient;
import com.example.planetsuperheroes.network.OrderApi;
import com.example.planetsuperheroes.models.Order;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Historial_Compras extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_compras);

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cargar el historial de órdenes
        cargarHistorialOrders();
    }

    private void cargarHistorialOrders() {
        OrderApi orderApi = ApiClient.getRetrofitInstance().create(OrderApi.class);
        Call<List<Order>> call = orderApi.obtenerOrders();

        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarHistorialOrders(response.body());
                } else {
                    Toast.makeText(Historial_Compras.this, "Error al cargar el historial", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("Historial_Compras", "Error: " + t.getMessage());
                Toast.makeText(Historial_Compras.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarHistorialOrders(List<Order> orders) {
        orderAdapter = new OrderAdapter(orders);
        recyclerView.setAdapter(orderAdapter);
    }
}