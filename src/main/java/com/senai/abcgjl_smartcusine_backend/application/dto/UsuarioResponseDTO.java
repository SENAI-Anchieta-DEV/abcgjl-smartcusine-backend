package com.senai.abcgjl_smartcusine_backend.application.dto;

import com.senai.abcgjl_smartcusine_backend.domain.enums.TipoUsuario;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
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


