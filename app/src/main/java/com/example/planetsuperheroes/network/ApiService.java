package com.example.planetsuperheroes.network;

import com.example.planetsuperheroes.models.LoginRequest;
import com.example.planetsuperheroes.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest request);
}