package com.lecuong.sourcebase.mapper;

import com.lecuong.sourcebase.entity.ApiKey;
import com.lecuong.sourcebase.modal.response.apikey.ApiKeyResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author CuongLM
 * @created 20/05/2024 - 20:35
 * @project source-base
 */
@Component
@AllArgsConstructor
public class ApiKeyMapper {

    private ModelMapper modelMapper;

    public ApiKeyResponse to(ApiKey apiKey) {
        return modelMapper.map(apiKey, ApiKeyResponse.class);
    }
}
