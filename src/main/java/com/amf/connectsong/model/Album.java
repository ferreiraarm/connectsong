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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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
    @OneToMany(mappedBy = "tracks")
    @JoinColumn(name = "track_id", referencedColumnName = "id", nullable = false)
    private ArrayList<Track> tracks;

    @Basic(optional = false)
    @OneToMany(mappedBy = "artists")
    @JoinColumn(name = "artists_id", referencedColumnName = "id", nullable = false)
    private ArrayList<String> artists;

    @Basic(optional = false)
    @OneToMany(mappedBy = "genres")
    @JoinColumn(name = "genres_id", referencedColumnName = "id", nullable = false)
    private ArrayList<String> genres;

    @Column(name = "popularity")
    private int popularity;

    public ArrayList<String> getArtists() {
        return artists;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getTotalTracks() {
        return totalTracks;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public String getUrl() {
        return url;
    }

    public void setArtists(ArrayList<String> artists) {
        this.artists = artists;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTotalTracks(int totalTracks) {
        this.totalTracks = totalTracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
