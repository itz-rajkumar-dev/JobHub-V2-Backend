package com.profile.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
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

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth


                        .requestMatchers(HttpMethod.POST, "/profile/internal")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/profile/user/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/profile/me")
                        .authenticated()

                        // Employer profile public view
                        .requestMatchers(HttpMethod.GET, "/employer-profile/user/**")
                        .permitAll()

                        // Employer own profile
                        .requestMatchers(HttpMethod.GET, "/employer-profile/me")
                        .authenticated()

                        // Employer update/delete
                        .requestMatchers(HttpMethod.PUT, "/employer-profile/update/**")
                        .authenticated()

                        .requestMatchers(HttpMethod.DELETE, "/employer-profile/delete/**")
                        .authenticated()

                        .requestMatchers("/profile/**")
                        .authenticated()

                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}