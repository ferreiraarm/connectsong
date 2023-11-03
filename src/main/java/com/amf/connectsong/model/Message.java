package com.amf.connectsong.model;

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
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "message")
    private String message;

    @Column(name = "posted_time")
    private Date postedTime;

    @Basic(optional = false)
    @OneToMany(mappedBy = "authenticator")
    @JoinColumn(name = "authenticator_sender_id", referencedColumnName = "id", nullable = false)
    private Authenticator sender;

    @Basic(optional = false)
    @OneToMany(mappedBy = "authenticator")
    @JoinColumn(name = "authenticator_receiver_id", referencedColumnName = "id", nullable = false)
    private Authenticator receiver;
}
