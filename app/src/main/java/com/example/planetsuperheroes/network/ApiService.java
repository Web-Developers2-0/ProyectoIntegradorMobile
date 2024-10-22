package com.example.planetsuperheroes.network;

import com.example.planetsuperheroes.models.Category;
import com.example.planetsuperheroes.models.LoginRequest;
import com.example.planetsuperheroes.models.LoginResponse;
import com.example.planetsuperheroes.models.Order;
import com.example.planetsuperheroes.models.Product;
import com.example.planetsuperheroes.models.User;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // Métodos de autenticación
    @POST("/api/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    // Métodos de productos
    @GET("/api/products/")
    Call<List<Product>> getProducts(@Query("filter") String filter);

    @GET("/api/products/{id}/")
    Call<Product> getProduct(@Path("id") int productId);

    @PUT("/api/products/{id_product}/")
    Call<Product> updateProductStock(@Path("id_product") int productId, @Body Product product);

    // Métodos de categorías
    @GET("categories/")
    Call<List<Category>> getCategories();

    // Métodos de pedidos
    @GET("/api/orders/user/")
    Call<List<Order>> getUserOrders(@Header("Authorization") String token);

    @POST("/api/orders/create/")
    Call<Order> createOrder(@Header("Authorization") String token, @Body Map<String, Object> orderRequestBody);

    @GET("/api/user/")
    Call<User> getUser(@Header("Authorization") String token);


}
