package com.auction.dto;

import com.auction.entity.User;
import java.time.LocalDateTime;

public class UserResponseDto {

    private Long id;
    private String email;
    private String username;
    private String phoneNumber;
    private String birthDate;
    private String gender;
    private String userStatus;
    private String userRole;
    private Boolean emailVerified;
    private Boolean phoneVerified;
    private LocalDateTime createdAt;
    private Double averageRating; // 평균 평점

    // 생성자
    public UserResponseDto() {
    }

    // Entity에서 DTO로 변환하는 정적 메서드
    public static UserResponseDto fromEntity(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPhoneNumber(user.getPhoneNumber());

        if (user.getBirthDate() != null) {
            dto.setBirthDate(user.getBirthDate().toString());
        }

        if (user.getGender() != null) {
            dto.setGender(user.getGender().name());
        }

        if (user.getUserStatus() != null) {
            dto.setUserStatus(user.getUserStatus().name());
        }

        if (user.getUserRole() != null) {
            dto.setUserRole(user.getUserRole().name());
        }

        dto.setEmailVerified(user.getEmailVerified());
        dto.setPhoneVerified(user.getPhoneVerified());
        dto.setCreatedAt(user.getCreatedAt());

        return dto;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}