package com.company.security;

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

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // Public company logo
                        .requestMatchers("/company-logos/**")
                        .permitAll()

                        // Public company post GET APIs
                        .requestMatchers(HttpMethod.GET, "/company-posts/**")
                        .permitAll()
                        // Public GET APIs
                        .requestMatchers("/company/internal/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/company")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/company/owner/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/company/*")
                        .permitAll()

                        // Other company APIs require authentication
                        .requestMatchers("/company/**")
                        .authenticated()

                        .anyRequest().authenticated())

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}