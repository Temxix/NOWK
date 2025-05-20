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

    private List<MessageReceived> messages;
    private Context context;

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
            timestampText = itemView.findViewById(R.id.timestampText);
            messageText = itemView.findViewById(R.id.messageText);
        }
    }

    @NonNull
    @Override
    public MessageReceivedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageReceivedAdapter.ViewHolder holder, int position) {
        MessageReceived message = messages.get(position);
        holder.senderText.setText(message.getSender());
        holder.timestampText.setText(formatTimestamp(message.getTimestamp()));
        holder.messageText.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return messages.size();
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
