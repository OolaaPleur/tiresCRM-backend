package com.oolaa.TiresCRM.jwt.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private CustomUser user;

    private List<GrantedAuthority> authorities;

    public CustomUserDetails(CustomUser user, List<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public String getFullName() {
        return user.getFullName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Or a condition based on user entity
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Or a condition based on user entity
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Or a condition based on user entity
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
    //...
}
