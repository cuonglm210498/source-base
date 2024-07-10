package com.lecuong.sourcebase.controller.refreshtoken;

import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.modal.response.user.UserAuthResponse;
import com.lecuong.sourcebase.security.UserDetailsImpl;
import com.lecuong.sourcebase.security.UserDetailsServiceImpl;
import com.lecuong.sourcebase.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/refresh-token")
    public ResponseEntity<BaseResponse<UserAuthResponse>> refreshToken(@RequestHeader(value = "Authorization") String refreshToken) {

        if (refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }

        String typeOfToken = tokenProvider.getTypeOfToken(refreshToken);

        if ("refresh".equals(typeOfToken)) {
            String userName = tokenProvider.getUsername(refreshToken);
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(userName);

            String accessToken = tokenProvider.generateToken(userDetails);
            String refreshTokenResponse = tokenProvider.generateRefreshToken(userDetails, tokenProvider.getExpirationDate(refreshToken));
            UserAuthResponse userAuthResponse = new UserAuthResponse(accessToken, refreshTokenResponse);
            return ResponseEntity.ok(BaseResponse.ofSuccess(userAuthResponse));
        } else {
            throw new BusinessException(StatusTemplate.TOKEN_IS_NOT_REFRESH_TOKEN);
        }
    }
}
