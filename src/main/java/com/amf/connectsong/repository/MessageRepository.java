package com.amf.connectsong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.amf.connectsong.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

     List<Message> findAll();

     Message findById(long id);
} 