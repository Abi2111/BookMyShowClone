package com.example.BookMyShow.UserService.Service;

import com.example.BookMyShow.UserService.config.TwilioConfig;
import com.example.BookMyShow.UserService.dto.OtpRequest;
import com.example.BookMyShow.UserService.dto.OtpResponseDto;
import com.example.BookMyShow.UserService.dto.OtpStatus;
import com.example.BookMyShow.UserService.dto.OtpValidationRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
public class SmsService {

    @Autowired
    public TwilioConfig twilioConfig;
    Map<String,String> otpMap = new HashMap<>();
    public OtpResponseDto sendSMS(OtpRequest otpRequest){
        OtpResponseDto otpResponseDto = null;
        try{
            PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
            String otp = generateOtp();
            String otpMessage = "Dear customer, \n your OTP is "+otp+" for testing sending.";
            Message message = Message.creator(to,from,otpMessage).create();
            otpMap.put(otpRequest.getUsername(),otp);
            otpResponseDto =new OtpResponseDto(OtpStatus.DELIVERED,otpMessage);
            return otpResponseDto;
        }catch (Exception e){
            e.printStackTrace();
            otpResponseDto =new OtpResponseDto(OtpStatus.FAILED,"Failed to send");
            return otpResponseDto;
        }

    }

    public String validateOtp(OtpValidationRequest otpValidationRequest){
        String optSent = otpMap.get(otpValidationRequest.getUserName());
        if(otpValidationRequest.equals(optSent)){
            otpMap.remove(otpValidationRequest.getUserName());
            return "OTP is valid";
        }else{
            return "OTP is invalid";
        }
    }
    private String generateOtp(){
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}
