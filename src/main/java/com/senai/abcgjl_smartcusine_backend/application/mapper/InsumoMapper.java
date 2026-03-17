package com.senai.abcgjl_smartcusine_backend.application.mapper;

import com.senai.abcgjl_smartcusine_backend.application.dto.InsumoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.InsumoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.domain.entity.InsumoEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InsumoMapper {

    public InsumoEntity toEntity(InsumoRequestDTO dto){

        return InsumoEntity.builder()
                .nome(dto.nome())
                .unidadeMedida(dto.unidadeMedida())
                .quantidadeEstoque(dto.quantidadeEstoque())
                .dataValidade(dto.dataValidade())
                .build();
    }

    public InsumoResponseDTO toResponse(InsumoEntity entity){

        boolean alerta = entity.getDataValidade().isBefore(LocalDate.now().plusDays(7));

        return new InsumoResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getUnidadeMedida(),
                entity.getQuantidadeEstoque(),
                entity.getDataValidade(),
                entity.getQrCode(),
                alerta
        );
    }
}
