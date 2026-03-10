package com.senai.abcgjl_smartcusine_backend.application.mapper;

import com.senai.abcgjl_smartcusine_backend.application.dto.AlertaResponseDTO;
import com.senai.abcgjl_smartcusine_backend.domain.entity.AlertaEntity;
import org.springframework.stereotype.Component;

@Component
public class AlertaMapper {

    public static AlertaEntity toEntity(AlertaResponseDTO dto) {
        AlertaEntity alerta = new AlertaEntity();

        alerta.setIdAlerta(dto.getIdAlerta());
        alerta.setTipo(dto.getTipo());
        alerta.setMensagem(dto.getMensagem());
        alerta.setEquipamento(dto.getEquipamento());
        alerta.setTemporizador(dto.getTemporizador());

        return alerta;
    }

    public static AlertaResponseDTO toDTO(AlertaEntity entity) {
        return new AlertaResponseDTO(
                entity.getIdAlerta(),
                entity.getTipo(),
                entity.getMensagem(),
                entity.getEquipamento(),
                entity.getTemporizador()
        );
    }
}
