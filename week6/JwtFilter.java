package com.user.userservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Custom JWT filter that intercepts every request to validate the JWT token.
 * If valid, it sets the authenticated user in the SecurityContext.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    // Utility class used to validate and parse JWT tokens
    private final JwtUtil jwtUtil;

    // Constructor-based injection of JwtUtil
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Core filter logic that intercepts the request, extracts JWT, validates it,
     * and sets authentication in the Spring SecurityContext.
     *
     * @param request     Incoming HTTP request
     * @param response    Outgoing HTTP response
     * @param filterChain Filter chain to continue the request
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Get the Authorization header (expected format: "Bearer <token>")
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Check if the header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                // Extract token from header (remove "Bearer " prefix)
                String token = authHeader.substring(7);

                // Validate the token and extract the username
                String username = jwtUtil.validateTokenAndGetUsername(token);

                // Create an authentication token with username and empty authorities
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

                // Set authentication into the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (RuntimeException e) {
                // If token is invalid or expired, return 401 Unauthorized
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired JWT: " + e.getMessage());
                return;
            }
        }

        // Continue with the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
