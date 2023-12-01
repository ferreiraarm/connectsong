package com.amf.connectsong.service;

import com.amf.connectsong.model.User;
import com.amf.connectsong.repository.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public void addUser(User newUser) {
        userRepository.findByName(newUser.getName());
        userRepository.save(newUser);
    }

}
