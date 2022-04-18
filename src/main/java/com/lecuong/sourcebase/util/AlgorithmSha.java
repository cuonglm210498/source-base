package com.lecuong.sourcebase.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AlgorithmSha {

    private static final String HASH_ALGORITHM = "SHA-1";

    public static String hash(String password)  {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] digest = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }
}
