package com.lecuong.sourcebase.security.jwt;

import com.lecuong.sourcebase.security.jwt.model.JwtPayLoad;
import com.lecuong.sourcebase.security.jwt.util.JwtClaimKey;
import org.jose4j.jwt.consumer.InvalidJwtException;

import java.security.PublicKey;
import java.util.Map;

public class TokenConsumer {

    //Nhận về một token và chuyển token đó sang dạng map

    private String audience;
    private JwtParser parser;

    public TokenConsumer(String audience, PublicKey publicKey) {
        this.audience = audience;
        parser = new JwtParser(publicKey);
    }

    public JwtPayLoad consume(String token) throws InvalidJwtException {
        Map<String, Object> claims = parser.parseToken(token, audience);

        JwtPayLoad jwtPayload = new JwtPayLoad();

        jwtPayload.setId((Long) claims.get(JwtClaimKey.ID.getValue()));
        jwtPayload.setUserName((String) claims.get(JwtClaimKey.USERNAME.getValue()));
        jwtPayload.setRole((String) claims.get(JwtClaimKey.ROLE.getValue()));

        return jwtPayload;
    }
}
