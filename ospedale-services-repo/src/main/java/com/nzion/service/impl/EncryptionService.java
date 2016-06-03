package com.nzion.service.impl;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Created by Raghu Bandi on 04-Dec-2015.
 */
@Service
public class EncryptionService {

    private Cipher cipher;
    private static final String ENCRYPTION_STD = "AES";
    String encryptionKey = "MZygpewJsCpRrfOr";
    private Encryptor encryptor;

    public EncryptionService() {
        encryptor = new Encryptor(encryptionKey);
    }

    private SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_STD);
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        cipher = Cipher.getInstance(ENCRYPTION_STD);
        return secretKey;
    }
    public String getEncrypted(String plainString) throws Exception {
        return encryptor.encrypt(plainString);
    }

    public String getDecrypted(String encryptedString) throws Exception {
        return encryptor.decrypt(encryptedString);
    }
}
