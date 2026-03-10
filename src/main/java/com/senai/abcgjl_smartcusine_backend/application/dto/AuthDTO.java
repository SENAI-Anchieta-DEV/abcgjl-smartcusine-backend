package com.senai.abcgjl_smartcusine_backend.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthDTO {
    public record LoginRequest(
            @Schema(description = "E-mail de login", example = "exemplo@gmail.com")
            String email,
            @Schema(description = "Senha para login", example = "123456")
            String senha,
            @Schema(description = "Tipo de usuário", example = "Cozinheiro")
            String tipoUsuario
    ) {}
    public record TokenResponse(
            String token
    ) {}
}
