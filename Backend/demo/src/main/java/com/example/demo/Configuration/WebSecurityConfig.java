package com.example.demo.Configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session
                        -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(
                        "/api/admin/register",
                        "/api/admin/login",
                        "/api/patients/register",
                        "/api/patients/login",
                        "/api/doctors/register",
                        "/api/doctors/login",
                        "/api/auth/**"
                ).permitAll()
                .requestMatchers(
                        HttpMethod.GET,
                        "/profile-images/**",
                        "/api/patients/doctors",
                        "/api/patients/**",
                        "/api/doctors/**",
                        "/api/medical-history/**",
                        "/api/admin/dashboard",
                        "/api/admin/**",
                        "/api/prescriptions/**",
                        "/api/appointment/**",
                        "/api/departments/**",
                        "/api/billing/**",
                        "/api/patient-feedback/**",
                        "/api/reports/**"
                ).permitAll()
                .requestMatchers(
                        HttpMethod.POST,
                        "/api/prescriptions",
                        "/api/doctors/schedule",
                        "/api/doctors/leave",
                        "/api/medical-history",
                        "/api/departments",
                        "/api/billing",
                        "/api/patient-feedback",
                        "/api/admin/**"
                ).permitAll()
                .requestMatchers(
                        HttpMethod.PUT,
                        "/api/appointment/**",
                        "/api/doctors/accept/**",
                        "/api/doctors/reject/**",
                        "/api/doctors/complete/**",
                        "/api/doctors/**",
                        "/api/patients/**",
                        "/api/admin/**",
                        "/api/departments/**",
                        "/api/billing/**",
                        "/api/patient-feedback/**"
                ).permitAll()
                .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/doctors/**",
                        "/api/patients/**",
                        "/api/admin/**",
                        "/api/prescriptions/**",
                        "/api/medical-history/**",
                        "/api/departments/**",
                        "/api/billing/**",
                        "/api/patient-feedback/**"
                ).permitAll()
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()
                .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList(
                "http://127.0.0.1:5500",
                "http://localhost:5500"
        ));

        configuration.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(Arrays.asList("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source
                = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}
