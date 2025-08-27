package com.example.distributed_file_storage.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;

    public User(){}

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public String getPassword(){
        return password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(() -> "ROLE_USER");
    }
}
