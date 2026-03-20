package com.senai.abcgjl_smartcusine_backend.application.dto;

import com.senai.abcgjl_smartcusine_backend.domain.enums.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioResponseDTO {

    @Schema(description = "ID do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome completo do usuário", example = "João da Silva")
    private String nome;

    @Schema(description = "Email do usuário", example = "joao@email.com")
    private String email;

    @Schema(description = "Tipo do usuário", example = "ADMIN")
    private TipoUsuario tipo;

    public UsuarioResponseDTO(Long id, String nome, String email, TipoUsuario tipo){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }

    public Long getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public String getEmail(){
        return email;
    }

    public TipoUsuario getTipo(){
        return tipo;
    }
}


