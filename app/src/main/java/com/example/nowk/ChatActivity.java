package com.example.nowk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
//        ApiService apiService = RetrofitClient.getApiService();
//
//        Call<List<MessageReceived>> call = apiService.getMessages(username, recipient);
//        call.enqueue(new Callback<List<MessageReceived>>() {
//            @Override
//            public void onResponse(Call<List<MessageReceived>> call, Response<List<MessageReceived>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    messages.clear();
//                    // Проставляем isSentByMe (или аналог) для корректного отображения
//                    for (MessageReceived msg : response.body()) {
//                        msg.setSentByMe(username.equals(msg.getUsername()));
//                        messages.add(msg);
//                    }
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Log.e("ChatActivity", "Response error: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<MessageReceived>> call, Throwable t) {
//                Log.e("ChatActivity", "Failed to load messages", t);
//            }
//        });


        messages.clear();

        // Пример "захардкоженных" сообщений
        MessageReceived msg1 = new MessageReceived();
        msg1.setUsername("Alice");
        msg1.setRecipient(username);
        msg1.setContent("Привет! Как дела?");
        msg1.setSentByMe(username.equals("Alice"));
        msg1.setTimestamp("12:00");

        MessageReceived msg2 = new MessageReceived();
        msg2.setUsername(username); // ты сам
        msg2.setRecipient("Alice");
        msg2.setContent("Всё окей, а ты как?");
        msg2.setSentByMe(true);
        msg2.setTimestamp("12:00");

        MessageReceived msg3 = new MessageReceived();
        msg3.setUsername("Alice");
        msg3.setRecipient(username);
        msg3.setContent("Тоже норм. Скучала.");
        msg3.setSentByMe(username.equals("Alice"));
        msg3.setTimestamp("12:05");

        messages.add(msg1);
        messages.add(msg2);
        messages.add(msg3);

        adapter.notifyDataSetChanged();

    }
    public void sendMessage(View view) {
        // Предположим, у тебя есть EditText для текста
        EditText messageInput = findViewById(R.id.editText);
        String content = messageInput.getText().toString().trim();
        if (content.isEmpty()) {
            // Можно вывести ошибку или просто не отправлять
            return;
        }

        MessageRequest message = new MessageRequest(username, recipient, content);

        ApiService apiService = RetrofitClient.getApiService(); // твой способ получить ApiService

        Call<Void> call = apiService.postMessage(message);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Очистить поле ввода и/или показать успех
                    messageInput.setText("");
                } else {
                    Throwable t = new Throwable("Ошибка ответа сервера: " + response.code());
                    Log.e("ChatActivity", "Обработка ошибки ответа", t);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("ChatActivity", "Обработка ошибки сети", t);
            }
        });
    }
}
