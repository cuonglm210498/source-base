package com.lecuong.sourcebase.controller.sms;

import com.lecuong.sourcebase.controller.BaseController;
import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.service.SmsService;
import com.lecuong.sourcebase.util.OtpUtils;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CuongLM
 * @created 25/08/2023 - 11:38 PM
 * @project source-base
 */
@Data
@RestController
@RequestMapping("/otp")
public class SmsController extends BaseController {

    private final SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<BaseResponse<Void>> sendOTP(@RequestParam String phoneNumber) {
        String headerPhoneNumber = "+";
        phoneNumber = headerPhoneNumber.concat(phoneNumber);
        smsService.sendOTP(phoneNumber, OtpUtils.generateOTP());
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }
}
