package com.amf.connectsong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.dto.LoginDTO;
import com.amf.connectsong.dto.SignupDTO;
import com.amf.connectsong.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    AuthController() {
    }

    @PostMapping("")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginRequest) {
        try {
            return authService.login(loginRequest);
        } catch (Exception e) {
            if (e.getMessage() == "USER_NOT_FOUND") {
                return ResponseEntity.status(404).body("Error: User not found!");
            }

            if (e.getMessage() == "INVALID_PASSWORD") {
                return ResponseEntity.status(403).body("Invalid credentials!");
            }

            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @PostMapping("/user")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupDTO singUpRequest) {
        try {
            return authService.signUp(singUpRequest);
        } catch (Exception e) {
            if (e.getMessage() == "USERNAME_ALREADY_TAKEN") {
                return ResponseEntity.status(409).body("Error: Username is already taken!");
            }

            if (e.getMessage() == "EMAIL_ALREADY_TAKEN") {
                return ResponseEntity.status(409).body("Error: Email is already taken!");
            }

            if (e.getMessage() == "ROLE_NOT_FOUND") {
                return ResponseEntity.status(404).body("Error: Role not found!");
            }

            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

}
