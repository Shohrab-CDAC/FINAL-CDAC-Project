package com.example.user.utils;

import com.example.user.entity.Request;
import com.example.user.entity.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
    // Base URL for the API
    String BASE_URL = "http://192.168.82.136:4000";

    // API endpoint for user login
    @POST("/login/usr")
    Call<JsonObject> loginUser(@Body User user);

    // API endpoint for user registration
    @POST("/reg/usr")
    Call<JsonObject> registerUser(@Body User user);

    // API endpoint to get user by ID
    @POST("/login/emp/uh/{id}")
    Call<JsonObject> getUserById(@Path("id") int id);

    // API endpoint for making a blood donation request
    @POST("/request")
    Call<JsonObject> request(@Body Request request);
}
