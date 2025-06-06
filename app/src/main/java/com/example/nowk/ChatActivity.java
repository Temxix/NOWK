package com.example.nowk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        username = intent.getStringExtra("name");
        recipient = intent.getStringExtra("recipient");

        nameView = findViewById(R.id.nameView);
        nameView.setText(recipient);
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

        MessageReceived m;

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:00:01", "Первое сообщение. Не жди лёгких ответов.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:01:15", "Я готова слушать. Что у тебя на уме?", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:02:30", "Сначала пойми: здесь нет случайностей.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:03:45", "Твоя тишина — уже сообщение.", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:04:50", "Не каждый выдержит правду, но её не спрятать.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:05:10", "Что ты хочешь от меня?", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:06:25", "Собраться. Идти дальше. Без оправданий.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:07:40", "Ты не из тех, кто легко сдаётся.", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:08:55", "Я не обещаю лёгких решений. Но — я даю выбор.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:09:10", "Что будет дальше?", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:10:20", "Ты готова встретить правду лицом?", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:11:35", "Правда всегда тяжелее, чем кажется.", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:12:50", "Но без неё нельзя строить будущее.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:13:05", "Расскажи, что ты видишь?", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:14:20", "Мир не изменится, если ждать перемен с дивана.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:15:30", "Твои слова — вызов.", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:16:45", "Выбор всегда остаётся за тобой.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:17:55", "Что ты предлагаешь?", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:18:10", "Начать с честности. И с действия.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:19:25", "Хорошо. Я с тобой.", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:20:40", "Тогда не оглядывайся назад.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:21:55", "Пусть будет так.", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:23:10", "Жди меня завтра в восемь.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:24:25", "Я буду.", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:25:40", "Помни: тишина — это тоже ответ.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:26:55", "Поняла.", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:28:10", "Не теряй себя.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:29:25", "Я уже давно не та, кем была.", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:30:40", "Тогда значит идёшь в правильном направлении.", true);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:31:55", "Спасибо, TMX.", false);
        messages.add(m);

        m = new MessageReceived();
        m.setAll("TMX", recipient, "2025-06-06T18:33:10", "Это ещё не конец.", true);
        messages.add(m);



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

    public void back(View view){
        finish();
    }
}
