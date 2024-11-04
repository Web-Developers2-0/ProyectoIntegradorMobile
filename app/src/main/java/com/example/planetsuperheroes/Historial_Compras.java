package com.example.planetsuperheroes;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.planetsuperheroes.adapters.OrderAdapter;
import com.example.planetsuperheroes.models.Order;
import com.example.planetsuperheroes.models.UserCrudInfo;
import com.example.planetsuperheroes.network.RetrofitClient;
import com.example.planetsuperheroes.network.ApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Historial_Compras extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private ApiService apiService;
    private int userId; // Almacena el ID del usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_compras);

        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el servicio de la API utilizando RetrofitClient
        apiService = RetrofitClient.getClient(this).create(ApiService.class);

        // Llamar al método para obtener la información del usuario
        getUserCrudInfo();
    }

    private void getUserCrudInfo() {
        Call<UserCrudInfo> call = apiService.getUserCrudInfo();

        call.enqueue(new Callback<UserCrudInfo>() {
            @Override
            public void onResponse(Call<UserCrudInfo> call, Response<UserCrudInfo> response) {
                if (response.isSuccessful()) {
                    UserCrudInfo userCrudInfo = response.body();
                    if (userCrudInfo != null) {
                        userId = userCrudInfo.getId(); // Almacena el ID del usuario

                        // Ahora que tenemos el ID del usuario, obtenemos sus órdenes
                        getOrders(get);
                    }
                } else {
                    Log.e("HistorialCompras", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserCrudInfo> call, Throwable t) {
                Log.e("HistorialCompras", "Error: " + t.getMessage());
            }
        });
    }

    private void getOrders(int userId) {
        Call<List<Order>> call = apiService.obtenerOrders(userId);  // Pasa el ID del usuario en la llamada
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
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