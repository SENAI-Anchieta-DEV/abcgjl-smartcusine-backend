package com.senai.abcgjl_smartcusine_backend.application.mapper;

import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaInsumoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaInsumoEntity;

public class FichaTecnicaInsumoMapper {

    public static FichaTecnicaInsumoResponseDTO toDTO(FichaTecnicaInsumoEntity entity){

        FichaTecnicaInsumoResponseDTO dto = new FichaTecnicaInsumoResponseDTO();

        dto.setNomeInsumo(entity.getInsumo().getNome());
        dto.setNomePreparo(entity.getFichaTecnica().getNomePreparo());
        dto.setQuantidade(entity.getQuantidade());

        return dto;
    }
}
