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
    @Column(name = "id_alerta")
    private UUID idAlerta;

    private String tipo;
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "equipamento_id")
    private EquipamentoEntity equipamento;

    @ManyToOne
    @JoinColumn(name = "temporizador_id")
    private TemporizadorEntity temporizador;

    public void emitirAlerta() {}
}