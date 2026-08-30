package com.example.BookMyShow.UserService.dto;

public class OtpValidationRequest {
    private String userName;
    private String otp;

    public OtpValidationRequest(String userName, String otp) {
        this.userName = userName;
        this.otp = otp;
    }

    public OtpValidationRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
