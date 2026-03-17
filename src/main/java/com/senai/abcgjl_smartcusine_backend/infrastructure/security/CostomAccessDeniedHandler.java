package com.senai.abcgjl_smartcusine_backend.infrastructure.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component

public class CostomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        response.getWriter().write("""
        {
            "status": 403,
            "error": "Forbidden",
            "message": "Você não tem permissão para acessar este recurso",
            "path": "%s"
        }
        """.formatted(request.getRequestURI()));
    }
}