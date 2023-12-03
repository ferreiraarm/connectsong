package com.amf.connectsong.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import com.amf.connectsong.model.Message;
import com.amf.connectsong.repository.MessageRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class MessageController {
    
    @Autowired
    private MessageRepository repMessageObj;

    @GetMapping("api/message")
    public List<Message> selecionarTodasMessage(){
        return repMessageObj.findAll();
    }

    @GetMapping("api/message/{id}")
    public Message selecionarMessagePorId(@PathVariable long id){
        return repMessageObj.findById(id);
    }

    @PostMapping("/api/message/cadastro")
    public Message enviarMessage(@RequestBody Message objMessage){
        return repMessageObj.save(objMessage);
    }
   
}
