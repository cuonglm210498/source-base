package com.lecuong.sourcebase.mapper;

import com.lecuong.sourcebase.entity.RefreshToken;
import com.lecuong.sourcebase.modal.request.refreshtoken.RefreshTokenRequest;
import com.lecuong.sourcebase.modal.response.refreshtoken.RefreshTokenResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author CuongLM
 * @created 11/07/2024 - 00:48
 * @project source-base
 */
@Component
@AllArgsConstructor
public class RefreshTokenMapper {

    private ModelMapper modelMapper;

    public RefreshToken to(RefreshTokenRequest request) {
        return modelMapper.map(request, RefreshToken.class);
    }

    public RefreshTokenResponse to(RefreshToken refreshToken) {
        return modelMapper.map(refreshToken, RefreshTokenResponse.class);
    }
}
