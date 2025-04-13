package com.example.security.config;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AccountDto {

    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public AccountDto(String username, String password, Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
