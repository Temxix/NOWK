// com.example.nowk.model.MessageRequest.java
package com.example.nowk;

public class MessageRequest {
    private String sender;
    private String recipient;
    private String content;

    public MessageRequest(String sender, String recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }
}
