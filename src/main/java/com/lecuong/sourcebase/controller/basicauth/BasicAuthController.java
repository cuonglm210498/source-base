package com.lecuong.sourcebase.controller.basicauth;

import com.lecuong.sourcebase.modal.response.BaseResponseV2;
import com.lecuong.sourcebase.modal.response.apikey.ApiKeyResponse;
import com.lecuong.sourcebase.service.ApiKeyService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CuongLM
 * @created 20/05/2024 - 20:43
 * @project source-base
 */
@Data
@RestController
@RequestMapping("/basic-auth")
public class BasicAuthController {

    private final ApiKeyService apiKeyService;

    @GetMapping("/api-key")
    public ResponseEntity<BaseResponseV2<String>> apiKey(@RequestHeader(name = "api-key") String apiKey) {
        // code xử lý kiểm tra api-key
        ApiKeyResponse apiKeyResponse = apiKeyService.findByKey(apiKey);

        // code xử lý logic khác
        String response = "api-key";

        return ResponseEntity.ok(BaseResponseV2.ofSuccess(response));
    }
}
