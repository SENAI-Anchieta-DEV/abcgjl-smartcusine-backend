package com.senai.abcgjl_smartcusine_backend.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record EquipamentoResponseDTO(

        @Schema(description = "ID do equipamento", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID idEquipamento,

        @Schema(description = "Tipo do equipamento", example = "Forno")
        String tipo,

        @Schema(description = "Temperatura atual do equipamento", example = "180.0")
        Double temperaturaAtual,

        @Schema(description = "Temperatura ideal do equipamento", example = "200.0")
        Double temperaturaIdeal,

        @Schema(description = "ID da ficha técnica vinculada", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID fichaTecnicaId,

        @Schema(description = "Nome do preparo da ficha técnica vinculada", example = "Lasanha")
        String nomePreparo

) {
}