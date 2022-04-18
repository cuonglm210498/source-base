package com.lecuong.sourcebase.security.jwt.util;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

public class String2KeyConverter {

    private KeyFactory keyFactory;

    public String2KeyConverter() {
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

    public PrivateKey getPrivateKey(String privateKey) {
        PemReader reader = null;

        try {
            reader = new PemReader(new StringReader(privateKey));
            byte[] content;
            PemObject pemObject = reader.readPemObject();
            if (Objects.nonNull(pemObject)) {
                content = pemObject.getContent();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(content);
                return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            }
            throw new InvalidKeySpecException(privateKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public PublicKey getPublicKey(String publicKey) {
        PemReader reader = null;

        try {
            reader = new PemReader(new StringReader(publicKey));
            byte[] content;
            PemObject pemObject = reader.readPemObject();
            if (Objects.nonNull(pemObject)) {
                content = pemObject.getContent();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(content);
                return keyFactory.generatePublic(x509EncodedKeySpec);
            }
            throw new InvalidKeySpecException(publicKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
