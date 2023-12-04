package com.amf.connectsong.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amf.connectsong.model.Message;
import com.amf.connectsong.model.User;

public interface MessageRepository extends JpaRepository<Message, Long> {

     List<Message> findAll();

     Set<Message> findBySenderAndReceiver(User sender, User receiver);

     Optional<Message> findById(long id);
}