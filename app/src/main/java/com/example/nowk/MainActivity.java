package com.example.nowk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Intent intent = new Intent(this, DebugActivity.class);
        startActivity(intent);
        finish();


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editName = findViewById(R.id.usernameEditText);
        editKey = findViewById(R.id.privateKeyEditText);

        // ⬇⬇⬇ Добавлено: автоматическое заполнение ключа по имени
        editName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String username = s.toString().trim();
                if (!username.isEmpty()) {
                    SharedPreferences prefs = getSharedPreferences("keys", MODE_PRIVATE);
                    String savedKey = prefs.getString(username, null);
                    if (savedKey != null) {
                        editKey.setText(savedKey);
                    }
                }
            }
        });
    }

    public void login(View view) {
        String name = editName.getText().toString();
        String key = editKey.getText().toString();
        if(name.isEmpty() || key.isEmpty()) return;

        Intent intent = new Intent(MainActivity.this, UserListActivity.class);
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

    public void loadPrivateKey(View view) {
        String privateKey = loadPrivateKeyFromFile();
        if (privateKey != null) {
            editKey.setText(privateKey);
        } else {
            editKey.setText("Нет ключа");
        }
    }

    private String loadPrivateKeyFromFile() {
        try {
            FileInputStream fis = openFileInput("private_key.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
