package com.example.nowk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nowk.adapter.MessageReceivedAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageReceivedAdapter adapter;
    private List<MessageReceived> messages = new ArrayList<>();

    private final String username = "tmx";    // Твой логин
    private final String recipient = "alice"; // Имя получателя

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageReceivedAdapter(this, messages);
        recyclerView.setAdapter(adapter);

        loadMessages();
    }

    private void loadMessages() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<MessageReceived>> call = apiService.getMessages(username, recipient);

        call.enqueue(new Callback<List<MessageReceived>>() {
            @Override
            public void onResponse(Call<List<MessageReceived>> call, Response<List<MessageReceived>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    messages.clear();
                    // Проставляем isSentByMe (или аналог) для корректного отображения
                    for (MessageReceived msg : response.body()) {
                        msg.setSentByMe(username.equals(msg.getUsername()));
                        messages.add(msg);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("ChatActivity", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<MessageReceived>> call, Throwable t) {
                Log.e("ChatActivity", "Failed to load messages", t);
            }
        });
    }
}
