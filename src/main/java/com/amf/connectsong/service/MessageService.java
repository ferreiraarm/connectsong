package com.amf.connectsong.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amf.connectsong.config.jwt.JwtUtils;
import com.amf.connectsong.dto.MessageDTO;
import com.amf.connectsong.dto.MessageVO;
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

    public ResponseEntity<?> saveMessage(MessageDTO messageDTO, String token, String receiverUsername) {

        String username = jwtUtils.getUserNameFromJwtToken(token);

        Optional<User> senderUser = userRepository.findByUsername(username);
        if (!senderUser.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        Optional<User> receiver = userRepository.findByUsername(receiverUsername);
        if (!receiver.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        Message msg = new Message(messageDTO.getMessage(), receiver.get(), senderUser.get());

        messageRepository.save(msg);

        return ResponseEntity.ok("Message sended!");
    }

    public ResponseEntity<?> deleteMessage(int id, String token) {

        String username = jwtUtils.getUserNameFromJwtToken(token);

        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        Optional<Message> message = messageRepository.findById(id);
        if (!message.isPresent()) {
            throw new RuntimeException("MESSAGE_NOT_FOUND");
        }

        if (message.get().getSender().getId() != user.get().getId()) {
            throw new RuntimeException("MESSAGE_NOT_FOUND");
        }

        messageRepository.delete(message.get());

        return ResponseEntity.ok("Message deleted!");
    }

    public ResponseEntity<?> getMessages(String token, String username) {

        String currentUsername = jwtUtils.getUserNameFromJwtToken(token);

        Optional<User> currentUser = userRepository.findByUsername(currentUsername);
        if (!currentUser.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        Optional<User> receiverUser = userRepository.findByUsername(username);
        if (!receiverUser.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        Set<Message> messages = messageRepository.findBySenderAndReceiver(currentUser.get(), receiverUser.get());
        Set<MessageVO> messageVOs = new HashSet<MessageVO>();

        messages.forEach(message -> {
            MessageVO messageVO = new MessageVO(message);
            messageVOs.add(messageVO);
        });

        return ResponseEntity.ok(messageVOs);

    }

}
