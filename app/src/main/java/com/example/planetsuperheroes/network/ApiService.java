package com.example.planetsuperheroes.network;

import com.example.planetsuperheroes.models.LoginRequest;
import com.example.planetsuperheroes.models.LoginResponse;
import com.example.planetsuperheroes.models.User;
import com.example.planetsuperheroes.models.UserCrudInfo;
import com.example.planetsuperheroes.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    @POST("/api/register/")
    Call<UserResponse> registerUser(@Body User user);

    @GET("/api/user/")
    Call<UserCrudInfo> getUserCrudInfo();

    @PATCH("/api/user/")
    Call<Void> updateUserCrudInfo(@Body UserCrudInfo user);


}