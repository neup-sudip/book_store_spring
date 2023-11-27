package com.example.first.authanduser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE email = :email OR username = :username LIMIT 1", nativeQuery = true)
    User findByEmailOrUsername(String email, String username);

    @Query(value = "SELECT * FROM users WHERE (user_id <> :id) AND (email = :email OR username = :username) LIMIT 1", nativeQuery = true)
    User findByNotIdAndEmailOrUsername(long id, String email, String username);

    @Query(value = "SELECT * FROM users WHERE username = :username AND password = :password", nativeQuery = true)
    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);
}

