package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administradores")
public class AdministradorEntity extends Usuario {
    protected AdministradorEntity() {
    }

    public AdministradorEntity(String nome, String cpf, String senha) {
        super(nome, cpf, senha);
    }

    public void cadastrarUsuarios() {
    }

    public void editarUsuarios() {
    }

    public void excluirUsuarios() {
    }
}
