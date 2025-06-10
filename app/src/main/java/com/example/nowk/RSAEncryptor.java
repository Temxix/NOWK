package com.example.nowk;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAEncryptor {

    private static final String CIPHER_INSTANCE = "RSA/ECB/PKCS1Padding";
    private static final String KEY_FACTORY_INSTANCE = "RSA";

    /**
     * Шифрует сообщение с использованием закрытого ключа.
     *
     * @param message текстовое сообщение
     * @param privateKeyPEM закрытый ключ в формате PKCS#8
     * @return зашифрованное сообщение в виде Base64 строки
     */
    public static String encrypt(String message, String privateKeyPEM) {
        try {
            // Убираем PEM заголовки
            String privateKeyClean = privateKeyPEM
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            // Декодируем из Base64
            byte[] pkcs8Bytes = Base64.decode(privateKeyClean, Base64.DEFAULT);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8Bytes);

            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_INSTANCE);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] encryptedBytes = cipher.doFinal(message.getBytes());
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);

        } catch (Exception e) {
            throw new RuntimeException("Ошибка шифрования RSA", e);
        }
    }
}
