package com.example.planetsuperheroes.network;

import android.content.Context;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static AuthInterceptor authInterceptor = new AuthInterceptor();

    public static Retrofit getClient(Context context) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(authInterceptor);

        OkHttpClient client = okHttpClientBuilder.build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://planetsuperheroes-vxb1.onrender.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
    public static void setToken(String token) {
        authInterceptor.setToken(token);
    }
}