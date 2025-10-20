package com.auction.dto;

public class JwtTokenDto {

    private String grantType; // Bearer
    private String accessToken;
    private String refreshToken;
    private Long expiresIn; // 액세스 토큰 만료 시간 (초 단위)

    public JwtTokenDto() {
    }

    public JwtTokenDto(String grantType, String accessToken, String refreshToken, Long expiresIn) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
