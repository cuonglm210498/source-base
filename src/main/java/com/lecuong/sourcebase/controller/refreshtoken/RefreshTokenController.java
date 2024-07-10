package com.lecuong.sourcebase.controller.refreshtoken;

import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import com.lecuong.sourcebase.modal.request.refreshtoken.RefreshTokenRequest;
import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.modal.response.refreshtoken.RefreshTokenResponse;
import com.lecuong.sourcebase.modal.response.user.UserAuthResponse;
import com.lecuong.sourcebase.security.UserDetailsImpl;
import com.lecuong.sourcebase.security.UserDetailsServiceImpl;
import com.lecuong.sourcebase.security.jwt.JwtTokenProvider;
import com.lecuong.sourcebase.service.RefreshTokenService;
import com.lecuong.sourcebase.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author CuongLM
 * @created 13/06/2024 - 08:48
 * @project source-base
 */
@RestController
@RequestMapping("/api/v1/token")
public class RefreshTokenController {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/refresh-token")
    public ResponseEntity<BaseResponse<UserAuthResponse>> refreshToken(@RequestHeader(value = "Authorization") String refreshToken) {

        if (refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }

        String typeOfToken = tokenProvider.getTypeOfToken(refreshToken);

        if ("refresh".equals(typeOfToken)) {
            String userName = tokenProvider.getUsername(refreshToken);
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(userName);

            UserAuthResponse userAuthResponse = new UserAuthResponse();

            // Generate new access token
            String accessToken = tokenProvider.generateToken(userDetails);
            userAuthResponse.setAccessToken(accessToken);

            // Generate new refresh token
            RefreshTokenResponse refreshTokenSearch = refreshTokenService.findByUserNameAndRefreshToken(userName, refreshToken);
            if (DataUtils.isTrue(refreshTokenSearch)) {
                // xoá refresh token cũ
                refreshTokenService.delete(refreshTokenSearch.getId());

                // tạo refresh token mới
                Long userId = tokenProvider.getUserId(refreshToken);
                String refreshTokenResponse = tokenProvider.generateRefreshToken(userDetails, tokenProvider.getExpirationDate(refreshToken));
                refreshTokenService.save(new RefreshTokenRequest(userId, userName, refreshTokenResponse, tokenProvider.getExpirationDate(refreshToken)));
                userAuthResponse.setRefreshToken(refreshTokenResponse);
            }

            return ResponseEntity.ok(BaseResponse.ofSuccess(userAuthResponse));
        } else {
            throw new BusinessException(StatusTemplate.TOKEN_IS_NOT_REFRESH_TOKEN);
        }
    }
}
