package com.nulp.mobilepartsshop.core.service.security;

import com.nulp.mobilepartsshop.core.entity.security.EncryptionData;
import com.nulp.mobilepartsshop.exception.security.DecryptionException;
import com.nulp.mobilepartsshop.security.service.DecryptionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Service
public class RsaDecryptionServiceImpl implements DecryptionService {

    @Value("${rsa.cipher}")
    private String CIPHER;

    @Value("${rsa.key.algorithm}")
    private String KEY_ALGORITHM;

    @Value("${rsa.key.size}")
    private int KEY_SIZE;

    @Value("${rsa.private.key}")
    private String PRIVATE_KEY;

    @Value("${rsa.public.key}")
    private String PUBLIC_KEY;

    private PrivateKey _privateKey = null;

    @Override
    public EncryptionData getEncryptionData() {
        return EncryptionData.builder()
                .cipher(CIPHER)
                .keyAlgorithm(KEY_ALGORITHM)
                .publicKeyBase64(PUBLIC_KEY)
                .build();
    }

    @Override
    public String decrypt(String encrypted) throws DecryptionException {
        try {
            PrivateKey privateKey = getPrivateKey();
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encrypted);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new DecryptionException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PrivateKey getPrivateKey() throws DecryptionException {
        if (_privateKey == null) {
            try {
                byte[] keyBytes = Base64.getDecoder().decode(PRIVATE_KEY);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
                _privateKey = keyFactory.generatePrivate(keySpec);
            } catch (Exception e) {
                throw new DecryptionException("Failed to get private key", e);
            }
        }
        return _privateKey;
    }

    private void generateKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        System.out.println("Public Key:\n" + publicKey);
        System.out.println("Private Key:\n" + privateKey);
    }
}
