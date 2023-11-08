package com.lecuong.sourcebase.controller.apikey;

import com.lecuong.sourcebase.modal.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CuongLM
 * @created 28/08/2023 - 10:09 PM
 * @project source-base
 */
@RestController
@RequestMapping("/api-key")
public class ApiKeyController {

    /**
     * Truy cập API thông qua ApiKey mà không phải thông qua token
     */

    @PostMapping
    public ResponseEntity<BaseResponse<String>> test() {
        return ResponseEntity.ok(BaseResponse.ofSuccess("test"));
    }
}
