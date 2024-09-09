package com.djf.chat_boss.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

@Configuration
public class UserDetailsSecurity {
    public UserDetailsSecurity(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private PasswordEncoder passwordEncoder;

    @Bean
    public InMemoryUserDetailsManager user() {
        return new InMemoryUserDetailsManager(User.withUsername("dj").password(passwordEncoder.encode("password")).authorities("read").build());
    }
}
