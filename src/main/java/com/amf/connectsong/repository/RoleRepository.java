package com.amf.connectsong.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amf.connectsong.model.ERole;
import com.amf.connectsong.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
