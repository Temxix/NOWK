package com.example.nowk;

public class MessageWrapper {
    private MessageReceived message;
    private boolean mine;

    public MessageReceived getMessage() {
        return message;
    }

    public boolean isMine() {
        return mine;
    }
}
