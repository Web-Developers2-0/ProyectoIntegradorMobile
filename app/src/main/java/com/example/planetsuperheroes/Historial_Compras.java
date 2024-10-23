package com.example.planetsuperheroes;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.planetsuperheroes.adapters.OrderAdapter;
import com.example.planetsuperheroes.network.ApiClient;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.models.Order;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Historial_Compras extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private ApiService apiService;  // Añadimos la instancia de ApiService

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_compras);

        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el servicio de la API
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        // Llamar al método para obtener las órdenes
        getOrders();
    }

    private void getOrders() {
        Call<List<Order>> call = apiService.obtenerOrders();  // Aquí el interceptor añadirá el token automáticamente
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Si recibimos la lista de órdenes, las mostramos en el RecyclerView
                    mostrarHistorialOrders(response.body());
                } else {
                    Log.e("HistorialCompras", "Error en la respuesta: " + response.code());
                    Toast.makeText(Historial_Compras.this, "Error al obtener las órdenes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("HistorialCompras", "Error: " + t.getMessage());
                Toast.makeText(Historial_Compras.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarHistorialOrders(List<Order> orders) {
        orderAdapter = new OrderAdapter(orders);
        recyclerView.setAdapter(orderAdapter);
    }
}