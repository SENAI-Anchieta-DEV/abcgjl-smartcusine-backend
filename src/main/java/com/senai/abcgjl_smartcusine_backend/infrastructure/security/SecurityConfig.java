package com.senai.abcgjl_smartcusine_backend.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desativa CSRF para testes
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // libera todos endpoints
                )
                .httpBasic(basic -> basic.disable()) // desativa autenticação básica
                .formLogin(login -> login.disable()); // desativa login de formulário

        return http.build();
    }
}