package com.auction.dto;

import com.auction.entity.User;

public class UserRegisterDto {

    // 필수 정보
    private String email;
    private String password;
    private String username;

    // 선택 정보
    private String phoneNumber;
    private String birthDate; // String으로 받아서 파싱
    private String gender; // "MALE", "FEMALE", "OTHER"

    // 생성자
    public UserRegisterDto() {
    }

    public UserRegisterDto(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Entity 변환 메서드
    public User toEntity() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setUsername(this.username);
        user.setPhoneNumber(this.phoneNumber);

        // Gender 설정
        if (this.gender != null) {
            try {
                user.setGender(User.Gender.valueOf(this.gender.toUpperCase()));
            } catch (IllegalArgumentException e) {
                user.setGender(User.Gender.OTHER);
            }
        }

        // 기본값 설정
        user.setUserStatus(User.UserStatus.ACTIVE);
        user.setUserRole(User.UserRole.BUYER);
        user.setEmailVerified(false);
        user.setPhoneVerified(false);

        return user;
    }
}