package com.catchtable.util.jwt;

import com.catchtable.api.user.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.ACCESS_SECRET}")
    private String accessSecret;

    @Value("#{${jwt.ACCESS_EXPIRATION}}")
    private long accessExpiration;

    @Value("#{${jwt.REFRESH_EXPIRATION}}")
    private long refreshExpiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(accessSecret.getBytes());
    }

    public String generateAccessToken(String userName, UserRole role) {
        return Jwts.builder()
                   .subject(userName)
                   .claim("role", role)
                   .issuedAt(new Date())
                   .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                   .signWith(key)
                   .compact();
    }

    public String generateRefreshToken(String userName, UserRole role) {
        return Jwts.builder()
                   .subject(userName)
                   .claim("role", role)
                   .issuedAt(new Date())
                   .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                   .signWith(key)
                   .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                            .verifyWith(key)
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();
        String username = claims.getSubject();
        Object roleClaim = claims.get("role");

        if (roleClaim instanceof String role) {
            return new JwtAuthToken(username, token, List.of(new SimpleGrantedAuthority(role)));
        } else if (roleClaim instanceof List<?> roles) {
            List<GrantedAuthority> authorities = roles.stream()
                                                      .map(Object::toString)
                                                      .map(SimpleGrantedAuthority::new)
                                                      .collect(Collectors.toList());
            return new JwtAuthToken(username, token, authorities);
        }
        return new JwtAuthToken(username, token, null);
    }

    public String getUserName(String token) {
        return Jwts.parser()
                   .verifyWith(key)
                   .build()
                   .parseSignedClaims(token)
                   .getPayload()
                   .getSubject();
    }

    public void validate(String token) {
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration();
    }

    public String resolveToken(String token) {
        if (token == null) {
            return null;
        }

        return token.substring(7);
    }
}
