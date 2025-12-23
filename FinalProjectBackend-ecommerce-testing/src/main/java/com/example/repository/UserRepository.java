package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByPhone(String phone);
    
	Object findByEmailOrPhoneOrUsername(String identifier, String identifier2, String identifier3);

}
