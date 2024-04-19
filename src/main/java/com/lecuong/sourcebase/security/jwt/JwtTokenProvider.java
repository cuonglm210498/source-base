package com.lecuong.sourcebase.security.jwt;

import com.lecuong.sourcebase.security.UserDetailsImpl;
import com.lecuong.sourcebase.security.UserToken;
import com.lecuong.sourcebase.util.JsonConvertUtils;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final String JWT_SECRET = "XuwqBioSdzG2GzfJITTfNrr3uuTNelUd5YitxERn20EAuP9TCBGZ4XNCGRPrilstyomEos6WQpL7Bum7b" +
            "La1kRNNWw5D4D3JEU/RBrtjHvmWTpHTGyqeGWRjME7o60hg/GAOdBxM83c5k4CQd3JetDKFsLeKmhcq0PgBxpuuFTa2SX4i6Yt+kHltTUx4" +
            "i9FwE+4U0bI9J4ueJYlEvvqJ+Jqi8CbQ+WSdb9VylA==%";
    private final long JWT_EXPIRATION = 86400000L;

    public String generateToken(UserDetailsImpl userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
//        Date expiryDate = new Date(now.getTime() + 120000L);
        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getUser().getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .claim("userName", userDetails.getUser().getUserName())
                .claim("user", userDetails.getUser())
                .claim("authorities", userDetails.getAuthorities())
                .compact();
    }

    // Lấy thông tin user từ jwt
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public UserToken getUserInfo(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return JsonConvertUtils.hashToObject(claims.get("user"), UserToken.class);
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userName").toString();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
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
}
