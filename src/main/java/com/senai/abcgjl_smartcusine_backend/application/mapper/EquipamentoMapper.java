package com.senai.abcgjl_smartcusine_backend.application.mapper;

import com.senai.abcgjl_smartcusine_backend.application.dto.EquipamentoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;

public class EquipamentoMapper {

    public static EquipamentoResponseDTO toDTO(EquipamentoEntity entity) {
        return new EquipamentoResponseDTO(
                entity.getIdEquipamento(),
                entity.getTipo(),
                entity.getTemperaturaAtual(),
                entity.getTemperaturaIdeal(),
                entity.getFichaTecnica() != null ? entity.getFichaTecnica().getId() : null,
                entity.getFichaTecnica() != null ? entity.getFichaTecnica().getNomePreparo() : null
        );
    }
}
