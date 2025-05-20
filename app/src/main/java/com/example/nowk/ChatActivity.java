package com.example.nowk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    ListView listView;
    EditText editText;

    String key;
    String name;

    ArrayList<String> messages;
    ArrayAdapter<String> messageAdapter;

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

        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editText);

        messages = new ArrayList<>();



        messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);

        listView.setAdapter(messageAdapter);

        messages.add("Привет!");
        messages.add("Как дела?");
        messageAdapter.notifyDataSetChanged();
    }
    public void sendMessage(View view) {
        String message = editText.getText().toString();
        if (message.isEmpty()) return;
        messages.add(message);
        messageAdapter.notifyDataSetChanged();
        listView.smoothScrollToPosition(messages.size() - 1);

        editText.setText("");
    }
}

