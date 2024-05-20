package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.entity.ApiKey;
import com.lecuong.sourcebase.exceptionv2.BusinessCodeResponse;
import com.lecuong.sourcebase.exceptionv2.BusinessExceptionV2;
import com.lecuong.sourcebase.mapper.ApiKeyMapper;
import com.lecuong.sourcebase.modal.response.apikey.ApiKeyResponse;
import com.lecuong.sourcebase.repository.ApiKeyRepository;
import com.lecuong.sourcebase.service.ApiKeyService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author CuongLM
 * @created 20/05/2024 - 20:32
 * @project source-base
 */
@Data
@Service
public class ApiKeyServiceImpl implements ApiKeyService {

    private final ApiKeyMapper apiKeyMapper;
    private final ApiKeyRepository apiKeyRepository;

    @Override
    public ApiKeyResponse findByKey(String code) {
        Optional<ApiKey> apiKey = apiKeyRepository.findByCode(code);
        return this.checkApiKeyExist(apiKey);
    }

    private ApiKeyResponse checkApiKeyExist(Optional<ApiKey> apiKey) {
        if (apiKey.isPresent()) {
            return apiKeyMapper.to(apiKey.get());
        } else {
            throw new BusinessExceptionV2(BusinessCodeResponse.API_KEY_NOT_FOUND);
        }
    }
}
