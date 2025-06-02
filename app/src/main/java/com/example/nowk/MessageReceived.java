package com.example.nowk;

public class MessageReceived {
    private final String sender;
    private final String timestamp;
    private final String content;
    private boolean isMine;

    public MessageReceived(String sender, String content, String timestamp, boolean isMine) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.isMine = isMine;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }
}
