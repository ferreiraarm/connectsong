package com.amf.connectsong.dto;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileVO extends RepresentationModel<ProfileVO> implements Serializable {
    private String name;
    private String username;
    private String email;
    private String profilePicture;

    public ProfileVO(String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }
}
