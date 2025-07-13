package com.user.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for the User Service.
 * It sets up HTTP security rules and integrates a custom JWT filter.
 */
@Configuration
public class SecurityConfig {

    // Inject custom JWT filter to validate tokens for protected endpoints
    private final JwtFilter jwtFilter;

    // Constructor-based dependency injection
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /**
     * Defines the Spring Security filter chain for this microservice.
     *
     * @param http HttpSecurity object to configure security settings
     * @return SecurityFilterChain bean
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection since we're using JWT (stateless sessions)
            .csrf(csrf -> csrf.disable())

            // Make the application stateless; no HTTP sessions will be created
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Define authorization rules
            .authorizeHttpRequests(auth -> auth
                // Allow unauthenticated access to register and login endpoints
                .requestMatchers("/users/register", "/users/login").permitAll()
                
                // All other requests require authentication
                .anyRequest().authenticated()
            );

        // Register the custom JWT filter before Spring Security's authentication filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
