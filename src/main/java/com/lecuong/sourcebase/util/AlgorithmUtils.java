package com.lecuong.sourcebase.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author CuongLM
 * @created 08/11/2023 - 8:15 PM
 * @project source-base
 */
public class AlgorithmUtils {

    private AlgorithmUtils() {}

    private static final String SHA_ALGORITHM = "SHA-1";
    private static final String MD5_ALGORITHM = "md5";

    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5_ALGORITHM);
            byte[] messageDigset = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigset);
            String hashText = no.toString(16);
            return hashText.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hash(String password)  {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(SHA_ALGORITHM);
            byte[] digest = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }
}
