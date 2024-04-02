package com.api.expo.models;

import java.sql.Date;
import java.util.Collection;
import java.time.Instant;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "USERS")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty(value = "firstname")
    private String firstname;

    @JsonProperty(value = "lastname")
    private String lastname;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "email")
    private String email;

    private Boolean accountNonExpired = true;

    private Boolean accountNonLocked = true;

    private Boolean credentialsNonExpired = true;

    private Boolean enabled = false;

    private Date emailVerifyAt;

    @JsonProperty(value = "role_id")
    private Long role_id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.firstname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setPassword() {
    }

    public String getEmail() {
        return this.email;
    }

    public String getLastname() {
        return this.lastname;
    }

    public Date getEmailVerifyAt() {
        return this.emailVerifyAt;
    }

    public boolean isPresent() {
        return false;
    }

    public Date setEmailVerifyAt(Instant now) {
        return this.emailVerifyAt;
    }

    public User() {
        // Constructeur sans arguments
    }

    public User(Integer user_id) {
        this.id = user_id;
    }

    public Long getRoleId() {
        // TODO Auto-generated method stub
        return this.role_id;
    }

}
