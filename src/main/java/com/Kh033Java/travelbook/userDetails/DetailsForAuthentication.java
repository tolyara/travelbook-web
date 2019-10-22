package com.Kh033Java.travelbook.userDetails;

import com.Kh033Java.travelbook.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;
public class DetailsForAuthentication implements org.springframework.security.core.userdetails.UserDetails {
    private User user;
    public DetailsForAuthentication(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getType())).collect(Collectors.toList());
    }
    public Long getId() {
        return user.getId();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        return user.getLogin();
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
    public User getUserDetails() {
        return user;
    }
}