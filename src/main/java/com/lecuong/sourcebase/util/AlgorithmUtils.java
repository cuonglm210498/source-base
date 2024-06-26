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

    public static String hash(String input)  {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(SHA_ALGORITHM);
            byte[] digest = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public static String hashSha256(String input)  {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = messageDigest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xff & bytes[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public static String base64Decode(String s) {
        return org.apache.commons.codec.binary.StringUtils.newStringUtf8(org.apache.commons.codec.binary.Base64.decodeBase64(s));
    }

    public static byte[] base64DecodeToBytes(String s) {
        return org.apache.commons.codec.binary.Base64.decodeBase64(s);
    }

    public static String base64Encode(String s) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(s));
    }

    public static String base64Encode(byte[] bytes) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
    }
}
