package com.djf.chat_boss.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ApiKeyAuthentication extends AbstractAuthenticationToken {

    public ApiKeyAuthentication( String apiKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }
    private String apiKey;
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}
