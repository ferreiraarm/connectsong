package com.amf.connectsong.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileDTO implements Serializable {
    private String name;
    private String email;
}
