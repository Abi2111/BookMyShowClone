package com.example.BookMyShow.UserService.controller;

import com.example.BookMyShow.UserService.Service.JwtService;
import com.example.BookMyShow.UserService.Service.UserService;
import com.example.BookMyShow.UserService.dto.ApiResponse;
import com.example.BookMyShow.UserService.dto.LoginRequest;
import com.example.BookMyShow.UserService.dto.SignupRequest;
import com.example.BookMyShow.UserService.entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    public UserService userService;

    @Autowired
    public JwtService jwtService;
    @PostMapping("/authenticate/signup")
    public ResponseEntity<ApiResponse<UserEntity>> signup(@RequestBody SignupRequest signupRequest){
        UserEntity user = userService.createUser(signupRequest);
        String token = jwtService.generateToken(signupRequest.getEmail());
        System.out.println(token);
        ResponseCookie cookie = ResponseCookie.from("bearer",token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(3600)
                .sameSite("Strict")
                .build();
        ApiResponse<UserEntity> res = new ApiResponse<>(
                "Success","User created successfully",user,null
        );
        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(res);
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate/login")
    public ResponseEntity<ApiResponse<String>> login(
            @RequestBody LoginRequest loginRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getEmail(),
                                loginRequest.getPassword()
                        )
                );

        if (authentication.isAuthenticated()) {

            String token =
                    jwtService.generateToken(loginRequest.getEmail());

            ResponseCookie cookie = ResponseCookie.from("bearer", token)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(3600)
                    .sameSite("Strict")
                    .build();

            ApiResponse<String> response = new ApiResponse<>(
                    "SUCCESS",
                    "Login successful",
                    token,
                    null
            );

            return ResponseEntity.ok()
                    .header("Set-Cookie", cookie.toString())
                    .body(response);
        }

        throw new RuntimeException("Invalid credentials");
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserEntity>> getUsers(){
        List<UserEntity> users = userService.allUsers();
        return  ResponseEntity.ok(users);
    }
}
