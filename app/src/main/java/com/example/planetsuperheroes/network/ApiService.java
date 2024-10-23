package com.example.planetsuperheroes.network;

import com.example.planetsuperheroes.models.LoginRequest;
import com.example.planetsuperheroes.models.LoginResponse;
import com.example.planetsuperheroes.models.Product;
import com.example.planetsuperheroes.models.UserCrudInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {
    @POST("/api/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    @GET("/api/user/")
    Call<UserCrudInfo> getUserCrudInfo();  // Token será manejado automáticamente por el interceptor

    @PATCH("/api/user/")
    Call<Void> updateUserCrudInfo(@Body UserCrudInfo user);


    // Métodos de productos
    @GET("/api/products/")
    Call<List<Product>> getProducts(@Query("filter") String filter);

    @GET("/api/products/{id}/")
    Call<Product> getProduct(@Path("id") int productId);


}
