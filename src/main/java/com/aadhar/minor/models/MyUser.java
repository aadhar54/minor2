package com.aadhar.minor.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MyUser implements UserDetails, Serializable {

    private static final String DELIMETER = ":";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String authorities;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String username;

    @OneToOne(mappedBy = "myUser")
    @JsonIgnoreProperties({"myUser"})
    private Student student;

    @OneToOne(mappedBy = "myUser")
    @JsonIgnoreProperties({"myUser"})
    private Admin admin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String [] auths = authorities.split(DELIMETER);
        return Arrays.stream(auths).map(a-> new SimpleGrantedAuthority(a)).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
