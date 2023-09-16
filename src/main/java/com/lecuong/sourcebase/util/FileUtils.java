package com.lecuong.sourcebase.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author CuongLM
 * @created 16/09/2023 - 10:31 PM
 * @project source-base
 */
public class FileUtils {

    public static String convertFileToBase64(MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        return Base64.encodeBase64String(fileContent);
    }

    public static byte[] convertMultipartFileToByteArray(MultipartFile multipartFile) throws IOException {
        return multipartFile.getBytes();
    }

    public static byte[] convertBase64ToByteArray(String base64String){
        return Base64.decodeBase64(base64String);
    }
}
