package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "insumos")
public class InsumoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idInsumo;

    private String nome;
    private String dataValidade;
    private String qrCode;
}
