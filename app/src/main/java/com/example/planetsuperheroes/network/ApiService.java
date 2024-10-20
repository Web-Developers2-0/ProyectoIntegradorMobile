package com.example.planetsuperheroes.network;

import com.example.planetsuperheroes.models.LoginRequest;
import com.example.planetsuperheroes.models.LoginResponse;
import com.example.planetsuperheroes.models.UserCrudInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;



public interface ApiService {
    @POST("/api/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    // Método GET para obtener la información del usuario por su ID
    @GET("swagger/user/{id}/")
    Call<UserCrudInfo> getUserInfo(@Path("userId") String userId);

    // Método PATCH para actualizar la información del usuario por su ID
    @PATCH("swagger/user/{id}/")
    Call<Void> updateUserInfo(@Path("userId") String userId, @Body UserCrudInfo user);
}

