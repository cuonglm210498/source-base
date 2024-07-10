package com.lecuong.sourcebase.controller.user;

import com.lecuong.sourcebase.entity.RefreshToken;
import com.lecuong.sourcebase.modal.request.refreshtoken.RefreshTokenRequest;
import com.lecuong.sourcebase.modal.request.user.UserAuthRequest;
import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.modal.response.refreshtoken.RefreshTokenResponse;
import com.lecuong.sourcebase.modal.response.user.UserAuthResponse;
import com.lecuong.sourcebase.security.UserDetailsImpl;
import com.lecuong.sourcebase.security.jwt.JwtTokenProvider;
import com.lecuong.sourcebase.service.RefreshTokenService;
import com.lecuong.sourcebase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/login")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping
    public ResponseEntity<BaseResponse<UserAuthResponse>> login(@RequestBody UserAuthRequest loginRequest) {

        userService.verifyUserNameAndPassword(loginRequest);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate access token
        String accessToken = tokenProvider.generateToken((UserDetailsImpl) authentication.getPrincipal());

        // delete all refresh token is active (giành cho trường hợp user spam api login)
        refreshTokenService.deleteAllByUserName(loginRequest.getUserName());

        // Generate refresh token
        String refreshToken = tokenProvider.generateRefreshToken((UserDetailsImpl) authentication.getPrincipal());
        Long userId = tokenProvider.getUserId(refreshToken);
        String userName = tokenProvider.getUsername(refreshToken);
        Date expiresDate = tokenProvider.getExpirationDate(refreshToken);
        refreshTokenService.save(new RefreshTokenRequest(userId, userName, refreshToken, expiresDate));

        UserAuthResponse userAuthResponse = new UserAuthResponse(accessToken, refreshToken);

        return ResponseEntity.ok(BaseResponse.ofSuccess(userAuthResponse));
    }
}
