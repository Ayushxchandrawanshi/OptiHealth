package com.example.demo.Configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.Security.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.startsWith("/api/auth/")
                || path.equals("/api/patients/register")
                || path.equals("/api/doctors/login")
                || path.equals("/api/doctors/register")
                || path.equals("/api/admin/register")
                || path.equals("/api/admin/login")
                || path.startsWith("/api/patients/")
                || path.startsWith("/api/doctors/")
                || path.startsWith("/profile-images/")
                || path.startsWith("/api/billing/")
                || path.startsWith("/api/appointment/")
                || path.startsWith("/api/departments/")
                || path.startsWith("/api/patient-feedback/")
                || path.startsWith("/api/reports/")
                || path.startsWith("/api/prescriptions/")
                || path.startsWith("/api/medical-history/")
                || path.startsWith("/swagger-ui/")
                || path.startsWith("/v3/api-docs/")) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        String authorizationHeader
                = request.getHeader("Authorization");

        String token = null;
        String email = null;

        if (authorizationHeader != null
                && authorizationHeader.startsWith("Bearer ")) {

            token
                    = authorizationHeader.substring(7);

            try {

                email
                        = jwtUtils.extractEmail(
                                token
                        );

            } catch (Exception e) {

                filterChain.doFilter(
                        request,
                        response
                );

                return;
            }
        }

        if (email != null
                && SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            UserDetails userDetails
                    = customUserDetailsService
                            .loadUserByUsername(
                                    email
                            );

            if (jwtUtils.validateToken(
                    token,
                    userDetails.getUsername()
            )) {

                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(
                                        request
                                )
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                                authenticationToken
                        );
            }
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}
