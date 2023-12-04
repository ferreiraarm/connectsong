package com.amf.connectsong.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewDTO implements Serializable {
    @NotNull
    private int grade;
    @NotBlank
    private String comment;
}
