package com.amf.connectsong.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "users")
public class User implements Serializable {

    public User(String name, String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "username")
    private String username;

    @Basic(optional = false)
    @Column(name = "email")
    private String email;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JoinColumn(name = "roulette_id", referencedColumnName = "id")
    private Roulette roulette;

    @Basic(optional = false)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false))
    private Set<Role> roles = new HashSet<>();

    @Basic(optional = true)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_address_id", referencedColumnName = "id", nullable = false)
    private UserAddress userAddress;

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
