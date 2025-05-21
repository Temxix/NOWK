  package com.example.nowk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void login(View view) {
        EditText editName = findViewById(R.id.usernameEditText);
        EditText editKey = findViewById(R.id.privateKeyEditText);
        if(editName.getText().toString().isEmpty() || editKey.getText().toString().isEmpty()) return;
        String name = editName.getText().toString();
        String key = editKey.getText().toString();
        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("key", key);
        startActivity(intent);
        finish();
    }
    public void register(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}