package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cozinheiros")
public class CozinheiroEntity extends Usuario {

    protected CozinheiroEntity() {}

    public CozinheiroEntity(String nome, String cpf, String senha) {
        super(nome, cpf, senha);
    }

    public void configurarTemporizador() {}
    public void registrarInsumo() {}
    public void consultarValidade() {}
    public void visualizarFichaTecnica() {}
}