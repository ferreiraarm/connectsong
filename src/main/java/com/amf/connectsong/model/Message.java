package com.amf.connectsong.model;

import java.util.Date;

import jakarta.persistence.Basic;
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
@Table(name = "message")
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "message")
    private String message;

    @Column(name = "posted_time")
    private Date postedTime;

    @Basic(optional = false)
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_sender_id", referencedColumnName = "id", nullable = false)
    private User sender;

    @Basic(optional = false)
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_receiver_id", referencedColumnName = "id", nullable = false)
    private User receiver;
}
