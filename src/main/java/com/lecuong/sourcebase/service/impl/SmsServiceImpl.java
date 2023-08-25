package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.service.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author CuongLM
 * @created 25/08/2023 - 11:30 PM
 * @project source-base
 */
@Service
@PropertySource({"classpath:application.properties"})
public class SmsServiceImpl implements SmsService {

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Override
    public void sendOTP(String recipientPhoneNumber, String otp) {
        Twilio.init(twilioAccountSid, twilioAuthToken);

        Message message = Message.creator(
                        new PhoneNumber(recipientPhoneNumber),
                        new PhoneNumber(twilioPhoneNumber),
                        "Your OTP is: " + otp)
                .create();

        System.out.println("Message SID: " + message.getSid());
    }
}
