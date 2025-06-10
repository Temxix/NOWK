package com.example.nowk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    UserListAdapter adapter;
    String currentUserName;
    String key;
    Handler handler = new Handler();

    List<ChatInfo> chatList = new ArrayList<>();
    TextView nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Intent intent = getIntent();
        currentUserName = intent.getStringExtra("name");
        key = intent.getStringExtra("key");

        nameView = findViewById(R.id.nameView);
        nameView.setText("       Hi, " + currentUserName + "!");
        recyclerView = findViewById(R.id.userRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadUserList();
        startPeriodicUpdate();
    }

    private void startPeriodicUpdate() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadUserList(); // загружаем свежие данные
                handler.postDelayed(this, 1000); // повтор через 1000 мс
            }
        }, 1000); // первая задержка — 1 секунда
    }


    private void loadUserList() {
        RetrofitClient.getApiService().getChatUserList(currentUserName).enqueue(new Callback<ChatListResponse>() {
            @Override
            public void onResponse(Call<ChatListResponse> call, Response<ChatListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chatList = response.body().getChats();  // используем chatList, а не users
                    Log.d("UserListActivity", "Чаты загружены: " + chatList.size());

                    adapter = new UserListAdapter(chatList, chat -> openChat(chat.getRecipient()));
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(UserListActivity.this, "Ошибка загрузки чатов", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChatListResponse> call, Throwable t) {
                Toast.makeText(UserListActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("UserListActivity", "Ошибка сети", t);
            }
        });
    }

    private void openChat(String recipientName) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("key", key);
        intent.putExtra("name", currentUserName);
        intent.putExtra("recipient", recipientName);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null); // убираем все запланированные обновления
    }
}
