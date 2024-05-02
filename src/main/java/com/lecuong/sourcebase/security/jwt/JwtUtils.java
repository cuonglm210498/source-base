package com.lecuong.sourcebase.security.jwt;

import com.lecuong.sourcebase.security.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author CuongLM
 * @created 02/05/2024 - 20:49
 * @project source-base
 */
@Component
public class JwtUtils {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    HttpServletRequest request;


    public String getUserName() {
        return jwtTokenProvider.getUsername(this.getToken());
    }

    public UserToken getUserInfo() {
        return jwtTokenProvider.getUserInfo(this.getToken());
    }

    public Long getUserId() {
        return jwtTokenProvider.getUserId(this.getToken());
    }

    public List<String> getRoles() {
        return jwtTokenProvider.getRoles(this.getToken());
    }

    private String getToken() {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return token.replace("Bearer ", "");
    }
}
