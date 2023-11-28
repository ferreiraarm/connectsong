package com.amf.connectsong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    UserController() {
    }

    @GetMapping("/api/users")
    String getUsers() {
        return "varios usuarios";
    }

    @GetMapping("/api/users/test")
    String getUsersTest() {
        return "testando o hot swapping";
    }

}
