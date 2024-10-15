package com.example.planetsuperheroes.network;

import com.example.planetsuperheroes.models.LoginRequest;
import com.example.planetsuperheroes.models.LoginResponse;

import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login/")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    static ApiService create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://planetsuperheroes.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }
}