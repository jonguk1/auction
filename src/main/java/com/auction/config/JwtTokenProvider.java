package com.auction.config;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private final long ACCESS_EXPIRATION_MS = 1000 * 60 * 60 * 1; // 1시간
    private final long REFRESH_EXPIRATION_MS = 1000L * 60 * 60 * 24 * 7; // 7일

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 액세스 토큰 생성
    public String generateAccessToken(String email) {
        return Jwts.builder()
                .subject(email)
                .signWith(getSigningKey())
                .expiration(new java.util.Date(System.currentTimeMillis() + ACCESS_EXPIRATION_MS))
                .compact();
    }

    // 리프레시 토큰 생성
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .subject(email)
                .signWith(getSigningKey())
                .expiration(new java.util.Date(System.currentTimeMillis() + REFRESH_EXPIRATION_MS))
                .compact();
    }

    // 토큰에서 이메일 추출
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 토큰이 유효하지 않은 경우
            return false;
        }
    }

}
