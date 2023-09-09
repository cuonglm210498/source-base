package com.lecuong.sourcebase.config;

import com.lecuong.sourcebase.security.jwt.TokenConsumer;
import com.lecuong.sourcebase.security.jwt.TokenProducer;
import com.lecuong.sourcebase.security.jwt.util.KeyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Configuration
@PropertySource({"classpath:token.properties"})
public class TokenManagerConfig {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.subject}")
    private String subject;

    @Value("${jwt.audience}")
    private String audience;

    @Value("#{'${jwt.audiences}'.split(',')}")
    private List<String> audiences;

    @Value("${jwt.expiration}")
    private long expirationInMin;

    @Value("${jwt.notBefore}")
    private float notBeforeInMin;

    @Value("${jwt.privateKey}")
    private String privateKeyPath;

    @Value("${jwt.publicKey}")
    private String publicKeyPath;

    @Bean
    public TokenProducer tokenProducer() {
        return createTokenProducer(expirationInMin);
    }

    private TokenProducer createTokenProducer(float time) {
        try {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(privateKeyPath);
            PrivateKey privateKey = KeyReader.getPrivateKey(resource.getURL().getPath());
            return new TokenProducer(issuer, subject, audiences.stream().toArray(size -> new String[size]),
                    time, notBeforeInMin, privateKey);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            return null;
        }
    }

    @Bean
    public TokenConsumer tokenConsumer() {
        try {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(publicKeyPath);
            PublicKey publicKey = KeyReader.getPublicKey(resource.getURL().getPath());
            return new TokenConsumer(audience, publicKey);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            return null;
        }
    }
}
