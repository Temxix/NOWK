package com.example.nowk;

import com.example.nowk.adapter.MessageReceivedAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;


public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editText;
    List<MessageReceived> messageReceivedList = new ArrayList<>();
    String key;
    String name;
    String recipient;
    MessageReceivedAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        key = intent.getStringExtra("key");
        recipient = intent.getStringExtra("recipient");
        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        // Настройка адаптера

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageReceivedList = new ArrayList<>();
        adapter = new MessageReceivedAdapter(this, messageReceivedList);
        recyclerView.setAdapter(adapter);

        // Загрузка сообщений с сервера
        loadMessages();
        TextView myTextView = findViewById(R.id.recView);
        myTextView.setText(recipient);

    }
    public void refreshMessages(View view) {
        loadMessages();
    }

    private void loadMessages() {
        // Временно захардкоженные сообщения между tmx и alice
//        messageReceivedList.clear();
//
//        messageReceivedList.add(new MessageReceived("tmx", "Привет, Alice!", "2025-05-21T15:00:00", true));
//        messageReceivedList.add(new MessageReceived("alice", "Привет, Tmx. Как дела?", "2025-05-21T15:01:30", false));
//        messageReceivedList.add(new MessageReceived("tmx", "Живу. Разбираю баги. А ты?", "2025-05-21T15:02:10", true));
//        messageReceivedList.add(new MessageReceived("alice", "Пишу стихи и жду, когда ты ответишь.", "2025-05-21T15:03:00", false));
//
//        adapter.notifyDataSetChanged();


    // Старый код с запросом
    ApiService apiService = RetrofitClient.getApiService();


    apiService.getMessages(name, recipient).enqueue(new Callback<>() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onResponse(@NonNull Call<List<MessageWrapper>> call, @NonNull Response<List<MessageWrapper>> response) {
            Log.d("ChatActivity", "Response code: " + response.code());
            Log.d("ChatActivity", "Response body: " + response.body());
            if (response.isSuccessful() && response.body() != null) {
                List<MessageWrapper> wrappers = response.body();
                messageReceivedList.clear();
                for (MessageWrapper wrapper : wrappers) {
                    if (Objects.equals(name, recipient) && !wrapper.isMine()) {
                        continue;
                    }
                    MessageReceived msg = wrapper.getMessage();
                    msg.setMine(wrapper.isMine());
                    messageReceivedList.add(msg);
                }
                adapter.notifyDataSetChanged();
            } else {
                Log.d("ChatActivity", "Response unsuccessful or empty body");
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<MessageWrapper>> call, @NonNull Throwable t) {
            Log.e("ChatActivity", "Failed to load messages", t);
        }
    });

    }

    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.editText);
        String content = editText.getText().toString().trim();
        if (content.isEmpty()) return;

        String sender = name;

        com.example.nowk.MessageRequest request = new com.example.nowk.MessageRequest(sender, recipient, content);

        RetrofitClient.getApiService().postMessage(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("ChatActivity", "Message sent");
                    loadMessages();
                    editText.setText("");
                } else {
                    Log.e("ChatActivity", "Failed: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("ChatActivity", "Error", t);
            }
        });
    }



}