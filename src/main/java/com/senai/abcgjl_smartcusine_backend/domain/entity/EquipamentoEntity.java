package com.senai.abcgjl_smartcusine_backend.domain.entity;

import com.senai. abcgjl_smartcusine_backend.domain.enums.StatusEquipamento;
import java.time.LocalDateTime;
import java.util.UUID;

public class EquipamentoEntity {

    private UUID id;
    private String nome;
    private Double temperaturaAtual;
    private Double temperaturaMinima;
    private Double temperaturaMaxima;
    private StatusEquipamento status;
    private LocalDateTime ultimaAtualizacao;

    public EquipamentoEntity(String nome, Double temperaturaMinima, Double temperaturaMaxima) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.temperaturaMinima = temperaturaMinima;
        this.temperaturaMaxima = temperaturaMaxima;
        this.status = StatusEquipamento.NORMAL;
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    public void atualizarTemperatura(Double novaTemperatura) {
        this.temperaturaAtual = novaTemperatura;
        this.ultimaAtualizacao = LocalDateTime.now();
        validarTemperatura();
    }

    private void validarTemperatura() {
        if (temperaturaAtual < temperaturaMinima || temperaturaAtual > temperaturaMaxima) {
            this.status = StatusEquipamento.ALERTA;
        } else {
            this.status = StatusEquipamento.NORMAL;
        }
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public Double getTemperaturaAtual() { return temperaturaAtual; }
    public StatusEquipamento getStatus() { return status; }
}
