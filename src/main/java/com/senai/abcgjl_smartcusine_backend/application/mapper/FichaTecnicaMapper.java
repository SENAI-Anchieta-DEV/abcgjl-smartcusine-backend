package com.senai.abcgjl_smartcusine_backend.application.mapper;

import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaResponseDTO;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import org.springframework.stereotype.Component;

@Component
public class FichaTecnicaMapper {

    public static FichaTecnicaEntity toEntity(FichaTecnicaRequestDTO dto) {

        FichaTecnicaEntity ficha = new FichaTecnicaEntity();

        ficha.setNomePreparo(dto.getNomePreparo());
        ficha.setTempoIdeal(dto.getTempoIdeal());
        ficha.setTemperaturaIdeal(dto.getTemperaturaIdeal());

        return ficha;
    }

    public static FichaTecnicaResponseDTO toResponseDTO(FichaTecnicaEntity entity) {

        return new FichaTecnicaResponseDTO(
                entity.getId(),
                entity.getNomePreparo(),
                entity.getTempoIdeal(),
                entity.getTemperaturaIdeal()
        );
    }

}
