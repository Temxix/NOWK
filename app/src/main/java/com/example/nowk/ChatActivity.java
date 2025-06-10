package com.example.nowk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;

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

    private String username;    // Твой логин
    private String recipient; // Имя получателя
    public TextView nameView;
    private Handler handler = new Handler();
    private Runnable periodicUpdate;
    private boolean isSending = false;
    private String key;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        username = intent.getStringExtra("name");
        recipient = intent.getStringExtra("recipient");
        key = intent.getStringExtra("key");
        startAutoUpdate();
        nameView = findViewById(R.id.nameView);
        nameView.setText(recipient);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageReceivedAdapter(this, messages);
        recyclerView.setAdapter(adapter);

        loadMessages();
    }

    private void startAutoUpdate() {
        periodicUpdate = new Runnable() {
            @Override
            public void run() {
                checkNewMessages();
                handler.postDelayed(this, 1000); // каждые 1 секунду
            }
        };
        handler.post(periodicUpdate);
    }

    private void checkNewMessages() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<ChatListResponse> call = apiService.getChatUserList(username);

        call.enqueue(new Callback<ChatListResponse>() {
            @Override
            public void onResponse(Call<ChatListResponse> call, Response<ChatListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (ChatInfo chat : response.body().getChats()) {
                        if (chat.getRecipient().equals(recipient)) {
                            if (chat.isHasNewMessages()) {  // <- ты должен убедиться, что это поле есть в `ChatInfo`
                                loadMessages(); // только если новые сообщения появились
                            }
                            break;
                        }
                    }
                } else {
                    Log.e("ChatActivity", "Ошибка получения чатов: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ChatListResponse> call, Throwable t) {
                Log.e("ChatActivity", "Ошибка сети при проверке новых сообщений", t);
            }
        });
    }

    public void buttonLoad(View view) {
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
                    for (MessageReceived rawMsg : response.body()) {
                        rawMsg.setUsername(username);   // устанавливаем локально
                        rawMsg.setRecipient(recipient); // чтобы знать, с кем чат
                        rawMsg.verifyIntegrity();       // ПРОВЕРЯЕМ ЦЕЛОСТНОСТЬ

                        messages.add(rawMsg);
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(messages.size() - 1);
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


    public void sendMessage(View view) {
        if (isSending) {
            Log.d("ChatActivity", "Сообщение уже отправляется — ждём.");
            return;
        }

        EditText messageInput = findViewById(R.id.editText);
        String content = messageInput.getText().toString().trim();
        if (content.isEmpty()) return;

        isSending = true; // блокируем повторную отправку
        String originalMessage = HashUtils.getHash(content);
        String privateKey = key; // ваш закрытый ключ
        String encryptedMessage = RSAEncryptor.encrypt(originalMessage, privateKey);
        MessageRequest message = new MessageRequest(username, recipient, content, encryptedMessage);
        ApiService apiService = RetrofitClient.getApiService();

        Call<Void> call = apiService.postMessage(message);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isSending = false; // разблокировка

                if (response.isSuccessful()) {
                    messageInput.setText("");
                    loadMessages();
                } else {
                    Log.e("ChatActivity", "Ошибка ответа сервера: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isSending = false; // даже если ошибка — надо разблокировать
                Log.e("ChatActivity", "Ошибка сети", t);
            }
        });
    }


    public void back(View view){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(periodicUpdate);
    }

}
