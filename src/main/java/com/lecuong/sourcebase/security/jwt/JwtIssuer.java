package com.lecuong.sourcebase.security.jwt;

import com.lecuong.sourcebase.security.jwt.util.String2KeyConverter;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

import java.security.PrivateKey;

public class JwtIssuer {

    //Dùng để khởi tạo ra mã token

    private PrivateKey privateKey;
    private String2KeyConverter string2KeyConverter;

    public JwtIssuer() {
        string2KeyConverter = new String2KeyConverter();
    }

    public JwtIssuer(String privateKey) {
        this();
        setPrivateKey(privateKey);
    }

    public JwtIssuer(PrivateKey privateKey) {
        this();
        this.privateKey = privateKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = string2KeyConverter.getPrivateKey(privateKey);
    }

    public String createToken(JwtClaims claims) {
        JsonWebSignature jsonWebSignature = new JsonWebSignature();
        jsonWebSignature.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        jsonWebSignature.setPayload(claims.toJson());
        jsonWebSignature.setKey(privateKey);

        try {
            return jsonWebSignature.getCompactSerialization();
        } catch (JoseException e) {
            throw new RuntimeException(e);
        }
    }
}
