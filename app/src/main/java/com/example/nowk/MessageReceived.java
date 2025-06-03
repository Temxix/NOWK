package com.example.nowk;

public class MessageReceived {
    private String username;    // вместо sender
    private String recipient;   // есть в ответе, у тебя нет в модели
    private String timestamp;
    private String content;
    private boolean sentByMe;  // вместо isMine

    public MessageReceived() {}

    // геттеры и сеттеры
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public boolean isSentByMe() { return sentByMe; }
    public void setSentByMe(boolean sentByMe) { this.sentByMe = sentByMe; }

    @Override
    public String toString() {
        return "MessageReceived{" +
                "username='" + username + '\'' +
                ", recipient='" + recipient + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", content='" + content + '\'' +
                ", sentByMe=" + sentByMe +
                '}';
    }
}
