package com.example.first.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email OR u.username = :username")
    User findByEmailOrUsername(String email, String username);

    @Query("SELECT u FROM User u WHERE (u.id <> :id) AND (u.email = :email OR u.username = :username)")
    User findByNotIdAndEmailOrUsername(long id, String email, String username);

}
