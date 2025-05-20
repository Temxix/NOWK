package com.example.nowk;

import android.os.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/messages")
    Call<List<com.example.nowk.Message>> getMessages(); // Message — твоя модель
}
