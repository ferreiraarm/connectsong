package com.amf.connectsong.model;

import java.util.ArrayList;
import java.util.Date;

import jakarta.persistence.Basic;
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
public class Album {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
    @Column(name = "total_tracks")
    private int totalTracks;
    @Column(name = "release_date")
    private Date releaseDate;

    @Basic(optional = false)
    @OneToMany(mappedBy = "album")
    private ArrayList<Track> tracks;

    @Basic(optional = false)
    @OneToMany(mappedBy = "album")
    private ArrayList<Artist> artists;

    @Basic(optional = false)
    @OneToMany(mappedBy = "album")
    private ArrayList<Genre> genres;

    @OneToMany(mappedBy = "album")
    private ArrayList<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "historic_id", referencedColumnName = "id", nullable = false)
    private Roulette historic;

    @ManyToOne
    @JoinColumn(name = "roulette_id", referencedColumnName = "id", nullable = false)
    private Roulette roulette;

    @Column(name = "popularity")
    private int popularity;
}
