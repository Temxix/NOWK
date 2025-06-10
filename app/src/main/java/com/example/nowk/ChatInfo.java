package com.example.nowk;

public class ChatInfo {
    private String recipient;
    private boolean hasNewMessages;
    private Long lastActivity; // может быть null

    public String getRecipient() {
        return recipient;
    }

    public boolean isHasNewMessages() {
        return hasNewMessages;
    }

    public Long getLastActivity() {
        return lastActivity;
    }
}
