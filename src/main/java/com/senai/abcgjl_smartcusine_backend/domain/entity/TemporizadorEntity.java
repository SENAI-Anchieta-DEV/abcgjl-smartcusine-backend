package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "temporizadores")
@NoArgsConstructor
@Setter
@Getter
public class TemporizadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idTemporizador;

    private Integer tempoConfigurado;
    private Integer tempoAtual;

    @OneToOne
    @JoinColumn(name = "equipamento_id")
    private EquipamentoEntity equipamento;

    @OneToMany(mappedBy = "temporizador", cascade = CascadeType.ALL)
    private List<AlertaEntity> alertas;


    public void iniciar() {}
    public void pausar() {}
    public void dispararAlerta() {}
}