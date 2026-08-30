package com.example.BookMyShow.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class OtpRequest {
    private String username;
    private String phoneNumber;

    public OtpRequest(String username, String phoneNumber) {
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    public OtpRequest() {
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
}
