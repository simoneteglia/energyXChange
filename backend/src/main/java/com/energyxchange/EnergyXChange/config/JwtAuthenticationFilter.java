// Import necessary packages
package com.energyxchange.EnergyXChange.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Make the class a component so it can be used throughout the application
@Component
// Use Lombok's RequiredArgsConstructor to create a constructor with all required final fields
@RequiredArgsConstructor
// Extend the OncePerRequestFilter so the filter only runs once per request
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Declare private fields
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Override the doFilterInternal method
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Get the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");
        // Declare variables for the JWT and the user's email
        final String jwt;
        final String userEmail;
        // If the Authorization header is null or does not start with "Bearer ", skip the filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Extract the JWT from the Authorization header
        jwt = authHeader.substring(7);
        // Extract the user's email from the JWT
        userEmail = jwtService.extractUsername(jwt);
        // If the user's email is not null and the user is not yet authenticated, authenticate the user
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user's details from the userDetailsService
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // If the JWT is valid for the user, create an authentication token and set it in the SecurityContext
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((request)));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}