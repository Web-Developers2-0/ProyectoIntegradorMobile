package com.example.planetsuperheroes.network;

import com.example.planetsuperheroes.models.Category;
import com.example.planetsuperheroes.models.LoginRequest;
import com.example.planetsuperheroes.models.LoginResponse;
import com.example.planetsuperheroes.models.Order;
import com.example.planetsuperheroes.models.OrderRequestBody;
import com.example.planetsuperheroes.models.Product;
import retrofit2.http.PUT;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/api/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    @GET("api/products/")
    Call<List<Product>> getProducts(@Query("filter") String filter);

    @GET("/api/products/{id}/")
    Call<Product> getProduct(@Path("id") int productId);
    @GET("categories/")
    Call<List<Category>> getCategories();

    @GET("api/orders/user/")
    Call<List<Order>> getUserOrders();

    @POST("api/orders/create/")
    Call<Order> createOrder(@Body OrderRequestBody orderRequestBody);

    @PUT("/api/products/{id_product}/")
    Call<Product> updateProductStock(@Path("id_product") int productId, @Body Product product);

}
