package com.auction.dto;

import com.auction.entity.UserProfile;
import com.auction.entity.User;

public class UserProfileDto {

    private Long profileId;
    private Long userId;
    private String profileImageUrl;
    private String bio;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private String preferredPaymentMethod;

    // 생성자
    public UserProfileDto() {
    }

    // Entity에서 DTO로 변환
    public static UserProfileDto fromEntity(UserProfile profile) {
        UserProfileDto dto = new UserProfileDto();
        dto.setProfileId(profile.getProfileId());

        if (profile.getUser() != null) {
            dto.setUserId(profile.getUser().getId());
        }

        dto.setProfileImageUrl(profile.getProfileImageUrl());
        dto.setBio(profile.getBio());
        dto.setAddress(profile.getAddress());
        dto.setCity(profile.getCity());
        dto.setPostalCode(profile.getPostalCode());
        dto.setCountry(profile.getCountry());
        dto.setPreferredPaymentMethod(profile.getPreferredPaymentMethod());

        return dto;
    }

    // DTO에서 Entity로 변환
    public UserProfile toEntity(User user) {
        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setProfileImageUrl(this.profileImageUrl);
        profile.setBio(this.bio);
        profile.setAddress(this.address);
        profile.setCity(this.city);
        profile.setPostalCode(this.postalCode);
        profile.setCountry(this.country);
        profile.setPreferredPaymentMethod(this.preferredPaymentMethod);

        return profile;
    }

    // Getter and Setter
    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPreferredPaymentMethod() {
        return preferredPaymentMethod;
    }

    public void setPreferredPaymentMethod(String preferredPaymentMethod) {
        this.preferredPaymentMethod = preferredPaymentMethod;
    }
}