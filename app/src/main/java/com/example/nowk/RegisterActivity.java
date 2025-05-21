package com.example.nowk;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView privateKeyTextView;
    private EditText usernameEditText;
    private Button copyButton;
    private String publicKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        privateKeyTextView = findViewById(R.id.privateKeyTextView);
        usernameEditText = findViewById(R.id.usernameEditText);
        copyButton = findViewById(R.id.copyButton);
    }

    public void register(View view) {
        privateKey();
    }
    public void login(View view) {
        privateKey();
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Private Key", text);
        clipboard.setPrimaryClip(clip);
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();
        RegisterRequest request = new RegisterRequest(username, publicKey);
        apiService.registerUser(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Toast.makeText(RegisterActivity.this, "Успешно зарегистрировано", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(RegisterActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void privateKey() {
        com.example.nowk.KeyGeneratorUtil keyUtil = new com.example.nowk.KeyGeneratorUtil();

        publicKey = keyUtil.getPublicKeyBase64();
        String privateKey = keyUtil.getPrivateKeyBase64();

        privateKeyTextView.setText(privateKey);

        copyButton.setOnClickListener(v -> {
            copyToClipboard(privateKey);
            Toast.makeText(this, "Скопируйте ключ", Toast.LENGTH_SHORT).show();
            registerUser();
        });
    }
}
