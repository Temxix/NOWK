package com.example.nowk;

public class RegisterRequest {
    private String name;
    private String publicKey;

    public RegisterRequest(String name, String publicKey) {
        this.name = name;
        this.publicKey = publicKey;
    }

    public String getName() {
        return name;
    }

    public String getPublicKey() {
        return publicKey;
    }
}
