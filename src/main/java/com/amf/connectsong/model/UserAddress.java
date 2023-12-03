package com.amf.connectsong.model;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
@Table(name = "user_address")
public class UserAddress implements Serializable{
    @Id
    @Column(name = "id")
    @PrimaryKeyJoinColumn
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "postal_code")
    private int postalCode;
    @Column(name = "country")
    private String country;
    @Column(name = "number")
    private int number;
    @Column(name = "address")
    private String address;

    @Basic(optional = false)
    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "userAddress", cascade = CascadeType.ALL)
    private User user;
}
