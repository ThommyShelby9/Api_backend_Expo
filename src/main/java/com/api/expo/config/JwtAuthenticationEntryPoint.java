package com.api.expo.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        if (request.getHeader(HttpHeaders.AUTHORIZATION) == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Missing Authorization header");
            return;
        }

        if (!request.getHeader(HttpHeaders.ACCEPT).contains(MediaType.APPLICATION_JSON_VALUE)) {
            response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type");
            return;
        }

        // Autres vérifications personnalisées si nécessaire...

        // Envoi de la réponse d'erreur par défaut
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication failed");
    }

}



