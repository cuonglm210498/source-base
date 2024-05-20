package com.lecuong.sourcebase.controller.basicauth;

import com.lecuong.sourcebase.entity.Client;
import com.lecuong.sourcebase.exceptionv2.BusinessCodeResponse;
import com.lecuong.sourcebase.exceptionv2.BusinessExceptionV2;
import com.lecuong.sourcebase.modal.response.BaseResponseV2;
import com.lecuong.sourcebase.modal.response.apikey.ApiKeyResponse;
import com.lecuong.sourcebase.modal.response.client.ClientResponse;
import com.lecuong.sourcebase.service.ApiKeyService;
import com.lecuong.sourcebase.service.ClientService;
import com.lecuong.sourcebase.util.AlgorithmUtils;
import lombok.Data;
import org.springframework.http.HttpStatus;
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

    private final ClientService clientService;
    private final ApiKeyService apiKeyService;

    /**
     * Basic authentication với api-key
     * Từ header của request truyền vào key "api-key" với value được cấp và lưu trong database
     * Kiểm tra xem value của api-key có tồn tại trong databse
     */
    @GetMapping("/api-key")
    public ResponseEntity<BaseResponseV2<String>> basicAuthWithApiKey(@RequestHeader(name = "api-key") String apiKey) {
        // code xử lý kiểm tra api-key
        ApiKeyResponse apiKeyResponse = apiKeyService.findByKey(apiKey);
        // End check api-key

        // code xử lý logic khác
        String response = "api-key";

        return ResponseEntity.ok(BaseResponseV2.ofSuccess(response));
    }

    /**
     * Basic authentication với authorization
     *
     * Từ header của request truyền vào key "Auth" với value là một đoạn mã hoá: "basic base64_encode",
     * Đoạn chuỗi base64_encode được mã hoá bởi hai chuỗi cách nhau bởi dấu ":" và bằng thuật toán mã hoá Base64
     * VD: SMART_OFFICE:SMART_OFFICE
     *
     * Sau khi nhận được thì backend sẽ giải mã chuỗi base64_encode và tách thành 1 mảng 2 phần tử ngăn cách bởi dấu chấm
     * Phần tử thứ nhất là clientId, phần tử thứ hai là clientPass
     * VD: SMART_OFFICE:SMART_OFFICE -> clientId = SMART_OFFICE, clientPass = SMART_OFFICE
     *
     * Tìm trong databse xem có tồn tại clientId và clientPass hay không? Nếu tồn tại thì cho đi qua, ngược lại thì báo lỗi
     * Note: clientPass lưu trong database là một đoạn mã được mã hoá theo một thuật toán nào đó, vì vậy khi tìm clientPass
     * thì mã hoá theo thuật toán và so sánh với giá trị trong databse
     * VD: SMART_OFFICE -> hashSha256 -> e616bc47f01d9e743ce04d74d23e74a172ecf3e3cd47f26764d2800053a99c1f
     */
    @GetMapping("/authorization")
    public ResponseEntity<BaseResponseV2<String>> basicAuthWithAuthorization(@RequestHeader(name = "Auth") String authorization) {

        // Check Basic Auth
        // basic U01BUlRfT0ZGSUNFOlNNQVJUX09GRklDRQ== (SMART_OFFICE:SMART_OFFICE)
        if (!authorization.toLowerCase().startsWith("basic")) {
            throw new BusinessExceptionV2(BusinessCodeResponse.INVALID_AUTHENTICATION_HEADER);
        }

        String base64Credentials = authorization.substring("basic".length()).trim();
        String credentials = AlgorithmUtils.base64Decode(base64Credentials);
        final String[] values = credentials.split(":", 2);
        if (values.length < 2) {
            throw new BusinessExceptionV2(BusinessCodeResponse.INVALID_AUTHENTICATION_HEADER);
        }

        String clientId = values[0];
        String clientPass = values[1];

        ClientResponse client = clientService.findByClientIdAndClientPass(clientId, clientPass);
        // Enc check basic auth

        String response = "Authorization";

        return ResponseEntity.ok(BaseResponseV2.ofSuccess(response));
    }
}
