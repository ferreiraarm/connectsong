package com.amf.connectsong.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "track")
public class Track {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "album_id", referencedColumnName = "id", nullable = false)
    private Album album;
    /**
     * Name of the track
     */
    @Column(name = "name")
    private String name;
    /**
     * Duration in miliseconds
     */
    @Column(name = "duration")
    private Integer duration;
    /**
     * Link to the track
     */
    @Column(name = "url")
    private String url;

    public Track(String name, Album album, String url, Integer duration) {
        this.name = name;
        this.album = album;
        this.url = url;
        this.duration = duration;
    }
}
