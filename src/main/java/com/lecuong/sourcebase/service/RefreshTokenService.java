package com.lecuong.sourcebase.service;

import com.lecuong.sourcebase.entity.RefreshToken;
import com.lecuong.sourcebase.modal.request.refreshtoken.RefreshTokenRequest;
import com.lecuong.sourcebase.modal.response.refreshtoken.RefreshTokenResponse;

import java.util.List;

/**
 * @author CuongLM
 * @created 11/07/2024 - 00:55
 * @project source-base
 */
public interface RefreshTokenService {

    void save(RefreshTokenRequest request);

    void delete(Long id);

    void deleteAllByUserName(String userName);

    List<RefreshTokenResponse> findAll();

    RefreshTokenResponse findByUserNameAndRefreshToken(String userName, String refreshToken);
}
