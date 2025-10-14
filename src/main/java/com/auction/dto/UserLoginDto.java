package com.auction.dto;

public class UserLoginDto {

    private String email;
    private String password;

    // 생성자
    public UserLoginDto() {
    }

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter and Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}