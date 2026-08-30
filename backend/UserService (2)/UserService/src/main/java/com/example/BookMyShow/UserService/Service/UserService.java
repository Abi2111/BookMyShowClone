package com.example.BookMyShow.UserService.Service;

import com.example.BookMyShow.UserService.dto.SignupRequest;
import com.example.BookMyShow.UserService.entities.UserEntity;
import com.example.BookMyShow.UserService.repo.UserRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    public PasswordEncoder passwordEncoder;

    public UserEntity createUser(SignupRequest signupRequest){
        Optional<UserEntity> isExists = userRepo.findByEmail(signupRequest.getEmail());
        if(isExists.isPresent()){
            throw new RuntimeException("User already exists");
        }
        UserEntity user = new UserEntity();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRoles("ROLE_USER");
        return userRepo.save(user);
    }

    public List<UserEntity> allUsers(){
        return userRepo.findAll();
    }

}
