package com.example.BookMyShow.UserService.repo;

import com.example.BookMyShow.UserService.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByEmail(String email);
}
