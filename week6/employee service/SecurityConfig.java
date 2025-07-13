package com.employee.employeeservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Indicates this class provides Spring Security configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter; // Injects the custom JWT filter

    /**
     * Defines the security filter chain for HTTP requests.
     *
     * @param http HttpSecurity object used to configure security behavior
     * @return SecurityFilterChain instance with custom security configuration
     * @throws Exception in case of configuration error
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disables CSRF protection for APIs (commonly disabled for stateless REST APIs)

            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // All requests must be authenticated (no public endpoints)
            )

            // Adds the JwtFilter before Spring's default UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Builds and returns the configured security filter chain
    }
}
