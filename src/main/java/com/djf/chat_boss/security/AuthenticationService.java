package com.djf.chat_boss.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public AuthenticationService(InMemoryUserDetailsManager userDetailsManager,PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }
    private final PasswordEncoder passwordEncoder;
    private final InMemoryUserDetailsManager userDetailsManager;
    private static final String AUTH_TOKEN_HEADER_NAME = "x-api-key";
    private static final String BASIC_AUTH_HEADER = "Authorization";
    @Value("${client-header-value}")
    private String clientHeaderValue;
    @Value("${client-name}")
    private String clientName;


    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        String basicAuth = request.getHeader(BASIC_AUTH_HEADER);
        String basicCreds = userDetailsManager.loadUserByUsername(clientName).getPassword();
        //System.out.println("basic= " + basicCreds + " matchTo= " +clientSecret);
        if (token == null || token.isEmpty() || !token.equals(clientHeaderValue) || basicAuth == null ||
                basicAuth.isEmpty() || passwordEncoder.matches(basicCreds, basicAuth)
        ) {
            throw new BadCredentialsException("Invalid Credentials");
        }
        return new ApiKeyAuthentication(token, AuthorityUtils.NO_AUTHORITIES);
    }
}
