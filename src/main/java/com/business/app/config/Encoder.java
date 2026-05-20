package com.business.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class Encoder {

    @Bean("bCrypt")
    public PasswordEncoder passwordEncoder(@Value("${encoder.strength}") int strength) {
        return new BCryptPasswordEncoder(strength);
    }
}
