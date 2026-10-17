package com.engagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth




                        // Temporary Feign test
                        .requestMatchers(
                                HttpMethod.GET,
                                "/likes/internal/test/**"
                        ).permitAll()
                        // Public GET APIs
                        .requestMatchers(
                                HttpMethod.GET,
                                "/likes/**",
                                "/comments/**",
                                "/follows/**"
                        ).permitAll()

                        // Like, Comment, Follow write APIs require login
                        .requestMatchers(
                                "/likes/**",
                                "/comments/**",
                                "/follows/**"
                        ).authenticated()

                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}