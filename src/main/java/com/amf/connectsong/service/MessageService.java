package com.amf.connectsong.service;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amf.connectsong.config.jwt.JwtUtils;
import com.amf.connectsong.dto.MessageDTO;
import com.amf.connectsong.model.Message;
import com.amf.connectsong.model.User;
import com.amf.connectsong.repository.MessageRepository;
import com.amf.connectsong.repository.UserRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> saveMessage(MessageDTO messageDTO, String token) {

        String username = jwtUtils.getUserNameFromJwtToken(token);
        
        Optional<User> senderUser = userRepository.findByUsername(username);
        if (!senderUser.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        Optional<User> receiver = userRepository.findByUsername(messageDTO.getReceiverUsername());
        if (!receiver.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        Message msg = new Message(messageDTO.getMessage(), receiver.get(), senderUser.get());

        messageRepository.save(msg);

        return ResponseEntity.ok("Message sended!");
    }

}
