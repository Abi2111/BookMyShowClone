package com.example.BookMyShow.UserService;

import com.example.BookMyShow.UserService.config.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableFeignClients
public class UserServiceApplication {

	@Autowired
	private TwilioConfig twilioConfig;

	@PostConstruct
	public void init(){
		System.out.println("SID: " + twilioConfig.getAccountSid());
		System.out.println("FROM: " + twilioConfig.getPhoneNumber());
		Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
