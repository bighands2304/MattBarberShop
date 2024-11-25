package com.barbershop.project.security;

import com.barbershop.project.model.Utente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(value = "password")
public class UtentePrincipal implements UserDetails {
    public UtentePrincipal(){}

    public UtentePrincipal(Utente utente){
        this.id = utente.getId();
        this.username = utente.getMail();
        this.password = utente.getPassword();
        this.enable = true;
        this.accountNonExpired = true;
        this.credentialExpired = true;
        this.accountNonLocked = true;
        this.authorities = Arrays.stream(utente.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Long id;
    private String username;
    private transient String password;
    private boolean enable;
    private boolean credentialExpired;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId(){
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}
