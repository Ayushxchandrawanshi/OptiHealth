package com.example.demo.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                // Disable CSRF
                .csrf(csrf -> csrf.disable())
                // Enable CORS
                .cors(Customizer.withDefaults())
                // JWT Session
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Authorization
                .authorizeHttpRequests(auth -> auth
                // Public APIs

                .requestMatchers(
                        "/api/patients/register",
                        "/api/patients/login",
                        "/api/doctors/register",
                        "/api/doctors/login",
                        "/api/admin/register",
                        "/api/admin/login",
                        "/api/auth/**"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/patients/doctors",
                        "/api/doctors/**"
                ).permitAll()
                // Swagger
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()
                // Everything Else

                .anyRequest().authenticated())
                // JWT Filter

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

}
