package com.amf.connectsong.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amf.connectsong.dto.LoginDTO;
import com.amf.connectsong.dto.SignupDTO;
import com.amf.connectsong.model.ERole;
import com.amf.connectsong.model.Role;
import com.amf.connectsong.model.User;
import com.amf.connectsong.repository.RoleRepository;
import com.amf.connectsong.repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public AuthService() {
    }

    public String login(LoginDTO newUser) {
        try {
            return "varios usuarios";
        } catch (Exception e) {
            return "";
        }
    }

    public ResponseEntity<?> signUp(SignupDTO singUpRequest) {

        if (userRepository.existsByUsername(singUpRequest.getUsername())) {
            throw new RuntimeException("USERNAME_ALREADY_TAKEN");
        }

        if (userRepository.existsByEmail(singUpRequest.getEmail())) {
            throw new RuntimeException("EMAIL_ALREADY_TAKEN");
        }

        User user = new User(
                singUpRequest.getName(),
                singUpRequest.getUsername(),
                singUpRequest.getEmail(),
                encoder.encode(singUpRequest.getPassword()));

        Set<String> strRoles = singUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("ROLE_NOT_FOUND"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("ROLE_NOT_FOUND"));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("ROLE_NOT_FOUND"));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

}
