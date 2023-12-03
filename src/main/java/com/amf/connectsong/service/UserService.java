package com.amf.connectsong.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amf.connectsong.config.jwt.JwtUtils;
import com.amf.connectsong.dto.ProfileDTO;
import com.amf.connectsong.model.User;
import com.amf.connectsong.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public void addUser(User newUser) {
        userRepository.findByUsername(newUser.getUsername());
        userRepository.save(newUser);
    }

    public ResponseEntity<?> getProfile(String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);

        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        ProfileDTO profileDTO = new ProfileDTO(user.get().getName(), user.get().getUsername(), user.get().getEmail());

        return ResponseEntity.ok(profileDTO);
    }

}
