package com.example.BookMyShow.UserService.config;

import com.example.BookMyShow.UserService.entities.UserEntity;
import com.example.BookMyShow.UserService.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    public UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepo.findByEmail(email);
        return user.map(UserInfoUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("User name not found"));
    }
}
