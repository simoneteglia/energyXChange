package com.energyxchange.EnergyXChange.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// specify that this is a configuration class
@Configuration
// enable Spring Security for web applications
@EnableWebSecurity
// indicate that this class requires non-null arguments for its constructor
@RequiredArgsConstructor
public class SecurityConfig  {

    // inject the JwtAuthenticationFilter and AuthenticationProvider instances
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // define a SecurityFilterChain bean to configure Spring Security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        // configure the HttpSecurity object
        httpSecurity.csrf()
                .disable() // disable CSRF protection
                .authorizeHttpRequests() // authorize all HTTP requests
                .requestMatchers("/api/v1/**") // allow unauthenticated access to some URLs
                .permitAll()
                .anyRequest() // require authentication for all other URLs
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // don't create a session
                .and()
                .authenticationProvider(authenticationProvider) // set the authentication provider
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // add the JwtAuthenticationFilter

        // build and return the SecurityFilterChain
        return httpSecurity.build();
    }

}

