package com.amf.connectsong.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.components.Mapper;
import com.amf.connectsong.dto.UserCreationDTO;
import com.amf.connectsong.model.User;
import com.amf.connectsong.repository.UserRepository;
import com.amf.connectsong.service.UserService;

@RestController
public class UserController {
    UserRepository repository;
    private Mapper mapper;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/users")
    String getUsers() {
        return "varios usuarios";
    }

    @PostMapping("/api/user")
    String addUser(@RequestBody UserCreationDTO newUser) {
        try {
            User user = mapper.toUser(newUser);
            UserService userService = new UserService(repository);
            userService.addUser(user);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "usuario agregado";
    }

}
