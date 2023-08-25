package com.lecuong.sourcebase.service;

/**
 * @author CuongLM
 * @created 25/08/2023 - 11:29 PM
 * @project source-base
 */
public interface SmsService {

    void sendOTP(String recipientPhoneNumber, String otp);
}
