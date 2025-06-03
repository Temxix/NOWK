package com.example.nowk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nowk.MessageReceived;
import com.example.nowk.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageReceivedAdapter extends RecyclerView.Adapter<MessageReceivedAdapter.ViewHolder> {

    private final List<MessageReceived> messages;
    private final Context context;

    private static final int VIEW_TYPE_MINE = 1;
    private static final int VIEW_TYPE_OTHER = 2;

    public MessageReceivedAdapter(Context context, List<MessageReceived> messages) {
        this.context = context;
        this.messages = messages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView senderText;
        TextView timestampText;
        TextView messageText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            senderText = itemView.findViewById(R.id.senderText);
            timestampText = itemView.findViewById(R.id.timeText);
            messageText = itemView.findViewById(R.id.messageText);
        }
    }

    @NonNull
    @Override
    public MessageReceivedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MINE) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_right, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_left, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageReceivedAdapter.ViewHolder holder, int position) {
        MessageReceived message = messages.get(position);
        holder.senderText.setText(message.getUsername());
        holder.timestampText.setText(formatTimestamp(message.getTimestamp()));
        holder.messageText.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).isSentByMe() ? VIEW_TYPE_MINE : VIEW_TYPE_OTHER;
    }

    private String formatTimestamp(String isoString) {
        try {
            if (isoString.contains(".")) {
                isoString = isoString.substring(0, isoString.indexOf("."));
            }
            LocalDateTime dateTime = LocalDateTime.parse(isoString);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            return dateTime.format(formatter);
        } catch (Exception e) {
            return isoString;
        }
    }
}
