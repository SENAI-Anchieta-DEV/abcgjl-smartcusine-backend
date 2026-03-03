package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "relatorios")
public class RelatorioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idRelatorio;

    private String tipo;
    private String data;

    public void gerarRelatorio() {}
}