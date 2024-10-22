package com.example.planetsuperheroes.network;

import com.example.planetsuperheroes.models.Order;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface OrderApi {
    @GET("orders") // Reemplaza "orders" con el endpoint correcto de tu API
    Call<List<Order>> obtenerOrders();
}