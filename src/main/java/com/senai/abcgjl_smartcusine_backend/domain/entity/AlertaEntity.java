package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "alertas")
public class AlertaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idAlerta;

    private String tipo;
    private String mensagem;

    @ManyToOne
    private EquipamentoEntity equipamento;

    @ManyToOne
    private TemporizadorEntity temporizador;

    protected AlertaEntity() {}

    public AlertaEntity(String tipo, String mensagem) {
        this.tipo = tipo;
        this.mensagem = mensagem;
    }

    public void emitirAlerta() {}
}