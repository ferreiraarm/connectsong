package com.amf.connectsong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.service.UserService;
import com.amf.connectsong.utils.ExceptionHandler;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    ExceptionHandler exceptionHandler;

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        try {
            return userService.getProfile(token);
        } catch (Exception e) {
            return exceptionHandler.returnException(e);
        }
    }
}
