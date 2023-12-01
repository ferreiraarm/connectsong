package com.amf.connectsong.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationDTO {
    private String name;
    private String username;
    private String phoneNumber;
    private String password;
    private String email;

    public UserCreationDTO() {
    }

    public UserCreationDTO(String name, String password, String username, String phoneNumber, String email) {
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
    }
}
