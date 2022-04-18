package com.lecuong.sourcebase.security;

import com.lecuong.sourcebase.security.jwt.TokenConsumer;
import com.lecuong.sourcebase.security.jwt.model.JwtPayLoad;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticator {

    private TokenConsumer consumer;

    @Autowired
    public TokenAuthenticator(TokenConsumer consumer) {
        this.consumer = consumer;
    }

    public Authentication getAuthentication(String token) throws InvalidJwtException {
        UserAuthentication userAuthentication = null;
        if (token != null) {
            JwtPayLoad jwtPayload = this.consumer.consume(token);
            UserDetails userDetails = new UserDetails();
            userDetails.setUser(jwtPayload);
            userAuthentication = new UserAuthentication(userDetails);
        }

        return userAuthentication;
    }
}
