package com.employee.employeeservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component // Marks this class as a Spring-managed component (used in the security config)
public class JwtFilter extends OncePerRequestFilter { // Ensures filter runs once per request

    @Autowired
    private JwtUtil jwtUtil; // Utility class to validate and extract username from JWT token

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the Authorization header from the request
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Check if the header is present and begins with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Remove "Bearer " prefix to extract the token
            String token = authHeader.substring(7);

            try {
                // Validate the token and extract the username
                String username = jwtUtil.validateTokenAndGetUsername(token);

                // Create an Authentication object with username and empty authorities
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

                // Set the authentication into the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // If token is invalid or expired, return 401 Unauthorized response
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                return;
            }
        }

        // Continue with the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
