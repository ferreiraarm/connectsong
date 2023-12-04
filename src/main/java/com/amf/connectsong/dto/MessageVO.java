package com.amf.connectsong.dto;

import com.amf.connectsong.model.Message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageVO {
    private int id;
    private String message;
    private String postedTime;
    private String sender;
    private String receiver;

    public MessageVO(Message message) {
        this.id = message.getId();
        this.message = message.getMessage();
        this.postedTime = message.getPostedTime().toString();
        this.sender = message.getSender().getUsername();
        this.receiver = message.getReceiver().getUsername();
    }
}
