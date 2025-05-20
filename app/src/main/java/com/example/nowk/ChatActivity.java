package com.example.nowk;

import com.example.nowk.adapter.MessageAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.ArrayAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;
import java.util.ArrayList;
import com.example.nowk.adapter.MessageAdapter;
import com.example.nowk.Message;



public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editText;
    List<Message> messageList = new ArrayList<>();
    String key;
    String name;
    MessageAdapter adapter;

// В onCreate



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
        messageList = new ArrayList<>();
        adapter = new MessageAdapter(this, messageList);
        recyclerView.setAdapter(adapter);

        // Загрузка сообщений с сервера
        loadMessages();

    }
    private void loadMessages() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getMessages().enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("ChatActivity", "Messages received: " + response.body().size());
                    for (Message msg : response.body()) {
                        Log.d("ChatActivity", "Message content: " + msg.getContent());
                    }

                    messageList.clear();
                    messageList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("ChatActivity", "Response unsuccessful or empty body");
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("ChatActivity", "Failed to load messages", t);
            }
        });
    }

    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();
        if (message.isEmpty()) return;


        editText.setText("");
    }
}