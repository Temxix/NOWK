package com.example.nowk;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // === Сообщения ===

    @POST("/api/messages")
    Call<Void> postMessage(@Body MessageRequest message);

    @GET("api/messages")
    Call<List<MessageReceived>> getMessages(
            @Query("username") String username,
            @Query("recipient") String recipient
    );

    // === Пользователи ===

    @POST("api/users/register")
    Call<Void> registerUser(@Body RegisterRequest request);

    @GET("api/users/names")
    Call<List<String>> getUserNames();

    @GET("api/users/welcome")
    Call<WelcomeResponse> getWelcomeMessage(@Query("name") String username);

    // === Чаты ===

    @GET("/api/users/chats/list")
    Call<ChatListResponse> getChatUserList(@Query("username") String username);

    @POST("api/users/chats")
    Call<Void> createChat(@Body ChatCreateRequest request);
}

