package ietosun.tidyapi.security.util;

import ietosun.tidyapi.user.entity.CustomUserDetail;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.InitBinder;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("{spring.application.name}")
    private String subject;

    private SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public String generateToken(CustomUserDetail customUserDetail) {
        return Jwts.builder()
                .claim("id", customUserDetail.getName())
                .claim("username", customUserDetail.getUsername())
                .claim("grade", customUserDetail.getAuthorities().toString())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(secretKey)
                .compact();
    }

    public Map<String, Object> getClaims(String token) {
        return (Map<String, Object>) Jwts.parser()
                .verifyWith(secretKey)
                .build().parse(token).getPayload();
    }
}
