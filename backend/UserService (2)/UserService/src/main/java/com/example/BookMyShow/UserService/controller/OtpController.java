package com.example.BookMyShow.UserService.controller;

import com.example.BookMyShow.UserService.Service.SmsService;
import com.example.BookMyShow.UserService.dto.OtpRequest;
import com.example.BookMyShow.UserService.dto.OtpResponseDto;
import com.example.BookMyShow.UserService.dto.OtpValidationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OtpController {
    @Autowired
    public SmsService smsService;
    @PostMapping("/opt-request")
    public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest){
        return smsService.sendSMS(otpRequest);
    }

    @PostMapping("/otp-verify")
    public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest){
        return smsService.validateOtp(otpValidationRequest);
    }
}
