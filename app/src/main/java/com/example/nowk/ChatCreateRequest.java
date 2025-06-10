package com.example.nowk;

public class ChatCreateRequest {
    private String username;
    private String recipient;

    public ChatCreateRequest(String username, String recipient) {
        this.username = username;
        this.recipient = recipient;
    }

    public String getUsername() {
        return username;
    }

    public String getRecipient() {
        return recipient;
    }
}
