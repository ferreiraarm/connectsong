package com.amf.connectsong.model;

import java.sql.Date;

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
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "grade")
    private String grade;

    @Column(name = "comment")
    private Date comment;

    @Basic(optional = false)
    @OneToMany(mappedBy = "authenticator")
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private Authenticator authenticator;

    @Basic(optional = false)
    @OneToMany(mappedBy = "album")
    @JoinColumn(name = "album_id", referencedColumnName = "id", nullable = false)
    private Album album;
}
