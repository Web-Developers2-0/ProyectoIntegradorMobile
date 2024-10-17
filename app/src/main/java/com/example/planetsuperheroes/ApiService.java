// ApiService.java

package com.example.planetsuperheroes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/products/")
    Call<List<Product>> getProducts(@Query("category") String category);

    @GET("categories/") // Asegúrate de que este endpoint sea correcto
    Call<List<Category>> getCategories();
}
