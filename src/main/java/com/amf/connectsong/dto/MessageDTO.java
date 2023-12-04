package com.amf.connectsong.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageDTO implements Serializable {
    @NotBlank
    private String message;

    @NotBlank
    private String receiverUsername;

}
