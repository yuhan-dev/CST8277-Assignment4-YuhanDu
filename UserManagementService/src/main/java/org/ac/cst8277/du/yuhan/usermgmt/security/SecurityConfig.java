package org.ac.cst8277.du.yuhan.usermgmt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for simplicity (API-based app)
                .csrf(csrf -> csrf.disable())

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Let your existing /auth/** endpoints be public
                        .requestMatchers("/", "/auth/**", "/error").permitAll()

                        // This endpoint requires the GitHub authenticated user
                        .requestMatchers("/me/token").authenticated()

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )

                // Enable OAuth2 Login (GitHub config comes from application.properties)
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}
