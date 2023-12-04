package com.amf.connectsong.dto;

import java.io.Serializable;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO implements Serializable {
    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    private Set<String> role;

    public SignupDTO() {
    }

    public SignupDTO(String name, String password, String username, String email,
            Set<String> role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
