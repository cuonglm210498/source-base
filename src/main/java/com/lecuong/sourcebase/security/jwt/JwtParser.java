package com.lecuong.sourcebase.security.jwt;

import com.lecuong.sourcebase.security.jwt.util.String2KeyConverter;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import java.security.PublicKey;
import java.util.Map;

public final class JwtParser {

    // Khi nhận về một token, JwtParser sẽ giải mã token đó để chúng ta có thể xử lý

    private PublicKey publicKey;
    private String2KeyConverter converter;

    public JwtParser() {
        converter = new String2KeyConverter();
    }

    public JwtParser(String publicKey) {
        this();
        setPublicKey(publicKey);
    }

    public JwtParser(PublicKey publicKey) {
        this();
        this.publicKey = publicKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = converter.getPublicKey(publicKey);
    }

    public Map<String, Object> parseToken(String token, String... audience) throws InvalidJwtException {
        JwtConsumerBuilder builder = new JwtConsumerBuilder().setRequireExpirationTime().setVerificationKey(publicKey);

        if (audience.length > 0) {
            builder.setExpectedAudience(audience);
        }

        JwtConsumer consumer = builder.build();
        JwtClaims claims = consumer.processToClaims(token);

        return claims.getClaimsMap();
    }
}
