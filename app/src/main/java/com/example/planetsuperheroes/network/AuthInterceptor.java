package com.example.planetsuperheroes.network;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AuthInterceptor implements Interceptor {
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder newRequest = request.newBuilder();

        if (token != null) {
            newRequest.addHeader("Authorization", "Bearer " + token);
        }

        return chain.proceed(newRequest.build());
    }
}