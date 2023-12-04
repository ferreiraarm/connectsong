package com.amf.connectsong.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO implements Serializable {
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String postalCode;
    @NotBlank
    private String country;
    @NotBlank
    private String number;
    @NotBlank
    private String address;

    public AddressDTO(String city, String state, String postalCode, String country, String number, String address) {
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.number = number;
        this.address = address;
    }
}
