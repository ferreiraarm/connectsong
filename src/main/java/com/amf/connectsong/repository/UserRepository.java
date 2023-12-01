package com.amf.connectsong.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amf.connectsong.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
