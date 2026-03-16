package com.senai.abcgjl_smartcusine_backend.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDTO {
    public record LoginRequest(


            @Schema(description = "E-mail de login", example = "exemplo@gmail.com")
            @NotBlank(message = "O email é obrigatório")
            @Email(message = "Email inválido")
            String email,


            @Schema(description = "Senha para login", example = "123456")
            @NotBlank(message = "A senha é obrigatória")
            String senha,


            @Schema(description = "Tipo de usuário", example = "Cozinheiro")
            @NotBlank(message = "O tipo de usuário é obrigatório")
            String tipoUsuario
    ) {}
    public record TokenResponse(
            String token
    ) {}
}
