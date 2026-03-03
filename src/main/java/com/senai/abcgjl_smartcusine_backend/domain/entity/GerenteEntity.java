package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "gerentes")
public class GerenteEntity extends Usuario {

    protected GerenteEntity() {}

    public GerenteEntity(String nome, String cpf, String senha) {
        super(nome, cpf, senha);
    }

    public void visualizarPainel() {}
    public void gerarRelatorio() {}
    public void consultarAlertas() {}
}