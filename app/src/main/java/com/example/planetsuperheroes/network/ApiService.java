package com.example.planetsuperheroes.network;

import com.example.planetsuperheroes.models.LoginRequest;
import com.example.planetsuperheroes.models.LoginResponse;
import com.example.planetsuperheroes.models.Order;
import com.example.planetsuperheroes.models.UserCrudInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;



public interface ApiService {
    @POST("api/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    @GET("api/user/")
    Call<UserCrudInfo> getUserCrudInfo();  // Token será manejado automáticamente por el interceptor

    @PATCH("/api/user/")
    Call<Void> updateUserCrudInfo(@Body UserCrudInfo user);

    @GET("/api/orders/user/") //
    Call<List<Order>> obtenerOrders();

}

