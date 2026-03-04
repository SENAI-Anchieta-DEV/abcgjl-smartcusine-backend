package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "administradores")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AdministradorEntity extends Usuario {

    public void cadastrarUsuarios() {
    }

    public void editarUsuarios() {
    }

    public void excluirUsuarios() {
    }
}
