package com.example.nowk;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("api/messages")
    Call<Void> postMessage(@Body MessageRequest message);

    @GET("api/users/names")
    Call<List<String>> getUserNames();

    @GET("/api/messages")
    Call<List<MessageReceived>> getMessages(
            @Query("username") String name,
            @Query("recipient") String recipient
    );


    @POST("api/users/register")
    Call<Void> registerUser(@Body RegisterRequest request);


}
