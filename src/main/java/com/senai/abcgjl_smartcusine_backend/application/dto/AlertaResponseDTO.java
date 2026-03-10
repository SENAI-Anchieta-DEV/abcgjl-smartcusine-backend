package com.senai.abcgjl_smartcusine_backend.application.dto;

import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.entity.TemporizadorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AlertaResponseDTO {

    private UUID idAlerta;
    private String tipo;
    private String mensagem;
    private EquipamentoEntity equipamento;
    private TemporizadorEntity temporizador;

}
