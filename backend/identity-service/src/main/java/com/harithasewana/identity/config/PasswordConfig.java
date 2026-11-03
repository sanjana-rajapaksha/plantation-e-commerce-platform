package com.harithasewana.identity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
@Configuration tells Spring that this class contains configuration.
@Bean tells Spring to manage the returned object.
PasswordEncoder is an interface that defines password encoding and matching operations.
BCryptPasswordEncoder is the implementation we're using.
*/

//Now Spring can inject a PasswordEncoder wherever we need it.

@Configuration
public class PasswordConfig {

    /* new BCryptPasswordEncoder() -> This creates an actual object of the BCryptPasswordEncoder class.
       @Bean -> This tells Spring: "Take the object returned by this method and manage it inside the Spring IoC container."
                So Spring creates and manages the PasswordEncoder object.*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

