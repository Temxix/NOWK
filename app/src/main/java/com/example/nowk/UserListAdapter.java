package com.example.nowk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private final List<ChatInfo> chatList;
    private final OnUserClickListener listener;

    public interface OnUserClickListener {
        void onUserClick(ChatInfo chat);
    }

    public UserListAdapter(List<ChatInfo> chatList, OnUserClickListener listener) {
        this.chatList = chatList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        ChatInfo chat = chatList.get(position);

        holder.usernameText.setText(chat.getRecipient());
        holder.itemView.setOnClickListener(v -> listener.onUserClick(chat));

        // Отобразить время последней активности
        if (chat.getLastActivity() != null) {
            String lastActivityStr = formatLastActivity(chat.getLastActivity());
            holder.lastActivityText.setText(lastActivityStr);
            holder.lastActivityText.setVisibility(View.VISIBLE);
        } else {
            holder.lastActivityText.setVisibility(View.GONE);
        }

        // Иконка нового сообщения
        if (chat.isHasNewMessages()) {
            holder.newMessageIcon.setVisibility(View.VISIBLE);
        } else {
            holder.newMessageIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText;
        TextView lastActivityText;
        ImageView newMessageIcon;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.usernameText);
            lastActivityText = itemView.findViewById(R.id.lastActivityText);
            newMessageIcon = itemView.findViewById(R.id.newMessageIcon);
        }
    }

    private String formatLastActivity(Long lastActivityMillis) {
        if (lastActivityMillis == null) return "";

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        Date date = new Date(lastActivityMillis);
        return sdf.format(date);
    }
}
