package com.bee.security.Filters;

import java.util.ArrayList;
import java.util.Collection;

import com.bee.models.User;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

//@RequiredArgsConstructor
//@Getter
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
@Getter
public class CustomAuthentication implements Authentication {

    private final User principal;

    @Setter
    private boolean authenticated = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}