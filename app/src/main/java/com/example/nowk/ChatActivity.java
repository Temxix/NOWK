package com.example.nowk;

import com.example.nowk.adapter.MessageReceivedAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;
import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editText;
    List<MessageReceived> messageReceivedList = new ArrayList<>();
    String key;
    String name;
    MessageReceivedAdapter adapter;
    private final Handler handler = new Handler();
    private final int REFRESH_INTERVAL_MS = 1000; // 1 секунда
    private final Runnable refreshMessagesRunnable = new Runnable() {
        @Override
        public void run() {
            loadMessages(); // твой метод получения сообщений
            handler.postDelayed(this, REFRESH_INTERVAL_MS);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        key = intent.getStringExtra("key");
        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        // Настройка адаптера
        messageReceivedList = new ArrayList<>();
        adapter = new MessageReceivedAdapter(this, messageReceivedList);
        recyclerView.setAdapter(adapter);

        // Загрузка сообщений с сервера
        loadMessages();
        handler.post(refreshMessagesRunnable);

    }
    private void loadMessages() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getMessages(name).enqueue(new Callback<List<MessageReceived>>() {
            @Override
            public void onResponse(Call<List<MessageReceived>> call, Response<List<MessageReceived>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("ChatActivity", "Messages received: " + response.body().size());
                    for (MessageReceived msg : response.body()) {
                        Log.d("ChatActivity", "Message content: " + msg.getContent());
                    }
                    messageReceivedList.clear();
                    messageReceivedList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("ChatActivity", "Response unsuccessful or empty body");
                }
            }

            @Override
            public void onFailure(Call<List<MessageReceived>> call, Throwable t) {
                Log.e("ChatActivity", "Failed to load messages", t);
            }
        });
    }

    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.editText);
        String content = editText.getText().toString().trim();
        if (content.isEmpty()) return;

        String sender = name;
        String recipient = name; //ИЗМЕНИТЬ!!!!

        com.example.nowk.MessageRequest request = new com.example.nowk.MessageRequest(sender, recipient, content);

        RetrofitClient.getApiService().postMessage(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("ChatActivity", "Message sent");
                } else {
                    Log.e("ChatActivity", "Failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("ChatActivity", "Error", t);
            }
        });

        editText.setText("");
    }



}