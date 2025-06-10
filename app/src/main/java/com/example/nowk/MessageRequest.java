package com.example.nowk;

public class MessageRequest {
    private String username;
    private String recipient;
    private String text;
    private String hash;

    public MessageRequest(String username, String recipient, String text, String hash) {
        this.username = username;
        this.recipient = recipient;
        this.text = text;
        this.hash = hash;
    }

    public String getUsername() { return username; }
    public String getRecipient() { return recipient; }
    public String getText() { return text; }
}

