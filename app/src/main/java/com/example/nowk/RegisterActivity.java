package com.example.nowk;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView privateKeyTextView;
    private EditText usernameEditText;
    private Button copyButton;
    private String publicKey;
    String privateKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        privateKeyTextView = findViewById(R.id.privateKeyTextView);
        usernameEditText = findViewById(R.id.usernameEditText);
    }

    public void register(View view) {
//        new Thread(() -> {
//            KeyGeneratorUtil keyUtil = KeyGeneratorUtil.generate();
//            publicKey = keyUtil.getPublicKeyBase64();
//            privateKey = keyUtil.getPrivateKeyBase64();
//
//            savePrivateKeyToFile(privateKey);
//            saveKeyToPreferences();
//
//            runOnUiThread(() -> {
//                privateKeyTextView.setText(privateKey); // безопасно для UI
                registerUser(); // ← всё готово, отправляем
//            });
//        }).start();
    }


    public void login(View view) {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void registerUser() {
        String username = usernameEditText.getText().toString();
//        if (username.isEmpty()) {
//            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        ApiService apiService = RetrofitClient.getApiService();
//        RegisterRequest request = new RegisterRequest(username, publicKey);
//        apiService.registerUser(request).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
//                Toast.makeText(RegisterActivity.this, "Успешно зарегистрировано", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
//                Toast.makeText(RegisterActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        Intent intent = new Intent(RegisterActivity.this, UserListActivity.class);
        intent.putExtra("name", username);
//        intent.putExtra("key", privateKey);
        startActivity(intent);
        finish();
    }

    public void privateKey() {
        KeyGeneratorUtil keyUtil = KeyGeneratorUtil.generate();

        publicKey = keyUtil.getPublicKeyBase64();
        privateKey = keyUtil.getPrivateKeyBase64();

        privateKeyTextView.setText(privateKey);
        savePrivateKeyToFile(privateKey); // <-- сохраняем в файл
        saveKeyToPreferences();           // <-- сохраняем в SharedPreferences
    }

    private void savePrivateKeyToFile(String privateKey) {
        try {
            FileOutputStream fos = openFileOutput("private_key.txt", MODE_PRIVATE);
            fos.write(privateKey.getBytes());
            fos.close();
            Toast.makeText(this, "Ключ сохранён", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка сохранения ключа", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // ⬇⬇⬇ добавлено: сохраняем username → privateKey в SharedPreferences
    private void saveKeyToPreferences() {
        String username = usernameEditText.getText().toString().trim();
        if (!username.isEmpty() && privateKey != null && !privateKey.isEmpty()) {
            SharedPreferences prefs = getSharedPreferences("keys", MODE_PRIVATE);
            prefs.edit().putString(username, privateKey).apply();
        }
    }
}
