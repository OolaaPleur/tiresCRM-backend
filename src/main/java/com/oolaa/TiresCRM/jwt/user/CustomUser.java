package com.oolaa.TiresCRM.jwt.user;

import com.oolaa.TiresCRM.jwt.user.authorities.Authorities;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class CustomUser extends User {
    private String fullName;
    @Id
    private String username;
    private String password;
    private boolean enabled;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Collection<Authorities> authorities;



    public CustomUser(String username, String password, String fullName, boolean enabled, Collection<Authorities> authorities) {
        super(username, password, authorities.stream()
                .map(a -> new SimpleGrantedAuthority(a.getAuthority()))
                .collect(Collectors.toList()));
        this.authorities = authorities;
        this.enabled = enabled;
        this.password = password;
        this.username = username;
        this.fullName = fullName;
    }

    protected CustomUser() {
        super("default", "default", Collections.emptyList());
        this.fullName = "default";
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    public void setAuthorities(Collection<Authorities> authorities) {
        this.authorities = authorities;
    }
}
