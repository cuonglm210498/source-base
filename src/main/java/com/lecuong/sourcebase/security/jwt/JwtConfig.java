package com.lecuong.sourcebase.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:application.properties"})
@RequiredArgsConstructor
public class JwtConfig {

    @Value("${token.expired}")
    private long expiration;

    @Value("${token.secretKey}")
    private String secretKey;

    @Value("${token.publicKey}")
    private String publicKey;

    @Value("${token.privateKey}")
    private String privateKey;

    public long getExpiration() {
        return expiration;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}
