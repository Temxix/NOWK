package com.example.nowk;

import android.util.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyGeneratorUtil {
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    private KeyGeneratorUtil(KeyPair keyPair) {
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    public static KeyGeneratorUtil generate() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair keyPair = keyGen.generateKeyPair();
            return new KeyGeneratorUtil(keyPair);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("RSA not supported on this device", e);
        }
    }

    public String getPublicKeyBase64() {
        return Base64.encodeToString(publicKey.getEncoded(), Base64.NO_WRAP);
    }

    public String getPrivateKeyBase64() {
        return Base64.encodeToString(privateKey.getEncoded(), Base64.NO_WRAP);
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}

