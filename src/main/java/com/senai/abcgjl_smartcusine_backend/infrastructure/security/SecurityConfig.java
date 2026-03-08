package com.senai.abcgjl_smartcusine_backend.infrastructure.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // CORRETO
public class SecurityConfig {
}
