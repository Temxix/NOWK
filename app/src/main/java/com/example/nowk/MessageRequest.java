package com.example.nowk;

public class MessageRequest {
    private final String username;
    private final String recipient;
    private final String content;

    public MessageRequest(String username, String recipient, String content) {
        this.username = username;
        this.recipient = recipient;
        this.content = content;
    }

    public String getSender() {
        return username;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }
}
