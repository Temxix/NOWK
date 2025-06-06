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
    List<String> users = new ArrayList<>();

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
    }

    private void loadUserList() {
//        RetrofitClient.getApiService().getUserNames().enqueue(new Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<String> usersList = response.body();
        List<String> usersList = Arrays.asList(
                "alice", "serge", "dima", "olga", "max", "lena", "ivan", "kate", "john", "maria",
                "peter", "nina", "alex", "irina", "viktor", "sofia", "oleg", "anna", "igor", "yulia",
                "mike", "dasha", "andrew", "sveta", "roman", "liza", "sergey", "tanya", "vasya", "natasha"
        );
        Log.d("UserListActivity", "Пользователи загружены: " + usersList.toString());
                    for (String user : usersList) {
                            users.add(user); // потом остальные
                    }
                    adapter = new UserListAdapter(users, username -> openChat(username));
                    recyclerView.setAdapter(adapter);
//                } else {
//                    Log.e("UserListActivity", "Ответ сервера не успешен, код: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//                Log.e("UserListActivity", "Ошибка загрузки пользователей", t);
//            }
//        });
    }


    private void openChat(String recipientName) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("key", key);
        intent.putExtra("name", currentUserName);
        intent.putExtra("recipient", recipientName);
        startActivity(intent);
    }
}
