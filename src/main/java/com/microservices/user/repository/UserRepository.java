package com.microservices.user.repository;

import com.microservices.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {


    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String username);

    @Query("select u from User u where u.userName=:userName Or u.email=:userName")
   Optional<User> findByUserNameOrEmail(@Param("userName") String userName);
}