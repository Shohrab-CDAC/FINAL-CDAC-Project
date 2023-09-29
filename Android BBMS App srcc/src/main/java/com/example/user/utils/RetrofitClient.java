package com.example.user.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient retrofitClient = null;
    private API api;

    // Private constructor to enforce singleton pattern
    private RetrofitClient(){
        // Create Retrofit instance with GsonConverterFactory and base URL
        api = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API.BASE_URL)
                .build()
                .create(API.class);
    }

    // Singleton pattern getInstance method
    public static RetrofitClient getInstance(){
        if(retrofitClient==null)
            retrofitClient = new RetrofitClient();
        return  retrofitClient;
    }

    // Getter for the API instance
    public API getApi() {
        return api;
    }
}
