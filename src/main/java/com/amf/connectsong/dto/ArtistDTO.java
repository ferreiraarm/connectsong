package com.amf.connectsong.dto;

import java.io.Serializable;

import com.amf.connectsong.model.Artist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ArtistDTO implements Serializable {
    private int id;

    private String name;

    public ArtistDTO(Artist artist) {
        this.id = artist.getId();
        this.name = artist.getName();
    }
}
