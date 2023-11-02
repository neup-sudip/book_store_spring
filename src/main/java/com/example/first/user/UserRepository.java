package com.example.first.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE email = email OR username = username", nativeQuery = true)
    User findByEmailOrUsername(String email, String username);

    @Query(value = "SELECT * FROM users WHERE (user_id <> id) AND (email = email OR username = username)", nativeQuery = true)
    User findByNotIdAndEmailOrUsername(long id, String email, String username);

}

