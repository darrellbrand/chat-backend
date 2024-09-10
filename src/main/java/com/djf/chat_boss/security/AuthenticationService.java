package com.djf.chat_boss.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final String AUTH_TOKEN_HEADER_NAME = "x-api-key";
    @Value("${client-header-value}")
    private String clientHeaderValue;

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (token == null || token.isEmpty() || !token.equals(clientHeaderValue)
        ) {
            throw new BadCredentialsException("Invalid Credentials");
        }
        return new ApiKeyAuthentication(token, AuthorityUtils.NO_AUTHORITIES);
    }
}
