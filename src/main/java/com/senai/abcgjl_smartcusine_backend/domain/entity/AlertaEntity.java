package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "alertas")
@Getter
@Setter
@NoArgsConstructor

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


    public void emitirAlerta() {}
}