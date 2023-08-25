package com.lecuong.sourcebase.util;

import java.util.Random;

/**
 * @author CuongLM
 * @created 25/08/2023 - 11:34 PM
 * @project source-base
 */
public class OtpUtils {

    //OTP generate is 6 number
    public static String generateOTP() {
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(digits.length());
            otp.append(digits.charAt(index));
        }

        return otp.toString();
    }
}
