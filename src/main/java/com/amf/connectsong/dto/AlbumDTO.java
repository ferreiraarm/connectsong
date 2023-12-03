package com.amf.connectsong.dto;

import java.io.Serializable;

import com.amf.connectsong.model.Album;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDTO implements Serializable {
    private int id;
    private String name;
    private String url;
    private Integer totalTracks;
    private String releaseDate;
    private int popularity;

    public AlbumDTO(Album album) {
        this.id = album.getId();
        this.name = album.getName();
        this.url = album.getUrl();
        this.totalTracks = album.getTotalTracks();
        this.releaseDate = album.getReleaseDate();
        this.popularity = album.getPopularity();
    }

}
