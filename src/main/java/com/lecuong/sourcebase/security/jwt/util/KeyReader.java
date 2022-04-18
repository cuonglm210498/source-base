package com.lecuong.sourcebase.security.jwt.util;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public final class KeyReader {

    //Class dùng để đọc file private.pem và public.pem

    public static PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        File file = new File(privateKey);
        PemObject pemObject = readPemFile(file);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(pemObject.getContent());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        return key;
    }

    public static PublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        File file = new File(publicKey);
        PemObject pemObject = readPemFile(file);

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pemObject.getContent());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(x509EncodedKeySpec);

        return key;
    }

    public static PemObject readPemFile(File file) throws IOException {
        PemReader pemReader = new PemReader(new FileReader(file));
        PemObject pemObject = pemReader.readPemObject();

        pemReader.close();

        return pemObject;
    }
}
