package com.lecuong.sourcebase.security.jwt;

import com.lecuong.sourcebase.security.UserDetailsImpl;
import com.lecuong.sourcebase.security.UserToken;
import com.lecuong.sourcebase.util.AlgorithmUtils;
import com.lecuong.sourcebase.util.JsonConvertUtils;
import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    private final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    private JwtConfig jwtConfig;

    public String generateToken(UserDetailsImpl userDetails) {
        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + 120000L);
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpiration());
        String jti = UUID.randomUUID().toString();
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setId(jti)
                .setIssuer("shop-project")
                .setSubject(Long.toString(userDetails.getUser().getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .setAudience("shop-project")
                .claim("userName", userDetails.getUser().getUserName())
                .claim("user", userDetails.getUser())
                .claim("authorities", userDetails.getAuthorities())
                .claim("jwk", AlgorithmUtils.base64Encode(jwtConfig.getSecretKey()))
                .claim("type", "access")
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecretKey())
                .compact();
    }

    public String generateRefreshToken(UserDetailsImpl userDetails) {
        Date now = new Date();
        // refresh token có thời hạn là 1 tuần
        Date expiryDate = new Date(now.getTime() + jwtConfig.getRefreshTokenExpiration());
        String jti = UUID.randomUUID().toString();
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setId(jti)
                .setIssuer("shop-project")
                .setSubject(Long.toString(userDetails.getUser().getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .setAudience("shop-project")
                .claim("userName", userDetails.getUser().getUserName())
                .claim("type", "refresh")
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecretKey())
                .compact();
    }

    // Lấy thông tin user từ jwt
    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public UserToken getUserInfo(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
        return JsonConvertUtils.hashToObject(claims.get("user"), UserToken.class);
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userName").toString();
    }

    public List<String> getRoles(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
        return (List<String>) claims.get("authorities");
    }

    public String getTypeOfToken(String token) {
        String[] chunks = token.split("\\.");
        java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
        String payloadJwt = new String(decoder.decode(chunks[1]));
        JSONObject payloadJson = new JSONObject(payloadJwt);
        return payloadJson.getString("type");
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return false;
    }

    public Claims verifyToken(String token) {
        return Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token).getBody();
    }

    public static Claims verifyToken(String token, String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        byte[] publicKey = Base64.decodeBase64(publicKeyStr);
        PublicKey key = kf.generatePublic(new X509EncodedKeySpec(publicKey));
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    public static Claims verifyToken(String token, byte[] publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey key = kf.generatePublic(new X509EncodedKeySpec(publicKey));
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
