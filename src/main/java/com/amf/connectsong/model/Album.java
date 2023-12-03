package com.amf.connectsong.model;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "album")
public class Album implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
    @Column(name = "total_tracks")
    private Integer totalTracks;
    @Column(name = "release_date")
    private String releaseDate;

    @Basic(optional = false)
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private Set<Track> tracks;

    @Basic(optional = false)
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private Set<Artist> artists;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "historic_id", referencedColumnName = "id", nullable = true)
    private Roulette historic;

    @ManyToOne
    @JoinColumn(name = "roulette_id", referencedColumnName = "id", nullable = true)
    private Roulette roulette;

    @Column(name = "popularity")
    private int popularity;

    public Album(String name, String url, Integer totalTracks, String releaseDate, Roulette roulette, int popularity) {
        this.name = name;
        this.url = url;
        this.totalTracks = totalTracks;
        this.releaseDate = releaseDate;
        this.roulette = roulette;
        this.popularity = popularity;
    }

}
