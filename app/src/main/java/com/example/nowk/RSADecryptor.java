package com.example.nowk;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

public class RSADecryptor {

    // Алгоритм шифрования
    private static final String RSA_MODE = "RSA/ECB/PKCS1Padding";
    // Имя провайдера (Android по умолчанию поддерживает BouncyCastle через SpongyCastle)
    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String CIPHER_PROVIDER = "AndroidOpenSSL"; // или "BC" для BouncyCastle

    /**
     * Расшифровывает данные с использованием закрытого ключа RSA.
     *
     * @param encryptedData зашифрованные данные в Base64 строке
     * @param privateKeyPem   приватный ключ в формате PKCS#8 (Base64 без PEM заголовков)
     * @return расшифрованная строка
     * @throws Exception если произойдет ошибка при расшифровке
     */
    public static String decrypt(String encryptedData, String privateKeyPem) throws Exception {
        // Убедимся, что мы очистили PEM заголовки
        String privateKeyPEM = privateKeyPem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        // Декодируем закрытый ключ из Base64
        byte[] decodedKey = Base64.decode(privateKeyPEM, Base64.DEFAULT);

        // Создаем спецификацию ключа
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);

        // Генерируем приватный ключ
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        // Инициализируем Cipher
        Cipher cipher = Cipher.getInstance(RSA_MODE, CIPHER_PROVIDER);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // Декодируем входные зашифрованные данные
        byte[] encryptedBytes = Base64.decode(encryptedData, Base64.DEFAULT);

        // Выполняем расшифровку
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes);
    }
}