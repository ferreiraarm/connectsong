package com.amf.connectsong.model;

import java.util.ArrayList;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "roulette")
public class Roulette {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic(optional = false)
    @OneToOne(mappedBy = "album")
    @JoinColumn(name = "album_id", referencedColumnName = "id", nullable = false)
    private ArrayList<Album> albums;

    @Basic(optional = false)
    @OneToOne(mappedBy = "album")
    @JoinColumn(name = "album_id", referencedColumnName = "id", nullable = false)
    private ArrayList<Album> historic;
}
