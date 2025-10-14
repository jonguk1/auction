package com.auction.dto;

public class UserUpdateDto {

    private String password; // 비밀번호 변경
    private String phoneNumber;
    private String birthDate;
    private String gender;

    // 생성자
    public UserUpdateDto() {
    }

    // Getter and Setter
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}