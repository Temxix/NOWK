package com.example.nowk;

import com.google.gson.annotations.SerializedName;

public class MessageReceived {
    @SerializedName("text")
    private String content;

    private String timestamp;
    private boolean sentByMe;

    // Новое поле
    @SerializedName("hash")
    private String hash;

    // Локальные переменные: не приходят с сервера
    private String username;
    private String recipient;

    // Флаг для проверки целостности
    private boolean isTampered = false;

    public void setAll(String username, String recipient, String timestamp, String content, boolean sentByMe) {
        this.username = username;
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.content = content;
        this.sentByMe = sentByMe;
    }

    // --- Геттеры и сеттеры ---

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(boolean sentByMe) {
        this.sentByMe = sentByMe;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean isTampered() {
        return isTampered;
    }

    public void verifyIntegrity() {
        if (hash == null || hash.isEmpty()) {
            isTampered = true;
            return;
        }
        String expectedHash = HashUtils.getHash(content);
        isTampered = !expectedHash.equals(hash);
    }
}