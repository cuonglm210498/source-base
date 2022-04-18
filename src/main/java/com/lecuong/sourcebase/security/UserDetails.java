package com.lecuong.sourcebase.security;

import com.lecuong.sourcebase.security.jwt.model.JwtPayLoad;
import com.lecuong.sourcebase.security.jwt.model.JwtPayLoad;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private HashSet<GrantedAuthority> authorities = new HashSet();
    private JwtPayLoad user;

    UserDetails() {
        this.authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return "password";
    }

    public String getUsername() {
        return this.user.getUserName();
    }

    public JwtPayLoad getUser() {
        return this.user;
    }

    void setUser(JwtPayLoad user) {
        this.user = user;
        String[] roles = user.getRole().split(",");
        for (String role : roles) {
            this.authorities.add(new SimpleGrantedAuthority(role));
        }
    }
}
