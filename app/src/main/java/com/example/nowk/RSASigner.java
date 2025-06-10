package com.example.nowk;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

public class RSASigner {

    private static final String TAG = "RSASigner";
    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /**
     * Подписывает сообщение с использованием закрытого ключа.
     *
     * @param message      текстовое сообщение
     * @param privateKeyPEM закрытый ключ в формате PEM (PKCS#8)
     * @return подпись в формате Base64
     */
    public static String sign(String message, String privateKeyPEM) {
        try {
            // Убираем PEM заголовки
            String privateKeyPEMClean = privateKeyPEM
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            // Декодируем из Base64
            byte[] decodedKey = Base64.decode(privateKeyPEMClean, Base64.DEFAULT);

            // Создаем приватный ключ
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // Подписываем
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(message.getBytes());

            byte[] signedBytes = signature.sign();
            return Base64.encodeToString(signedBytes, Base64.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при создании подписи", e);
        }
    }
}
