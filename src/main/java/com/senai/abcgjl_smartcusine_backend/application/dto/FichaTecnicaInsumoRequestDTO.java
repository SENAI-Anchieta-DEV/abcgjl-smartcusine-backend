package com.senai.abcgjl_smartcusine_backend.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record FichaTecnicaInsumoRequestDTO(

        @NotNull(message = "O ID da ficha técnica é obrigatório")
        @Schema(description = "ID da ficha técnica", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID fichaTecnicaId,

        @NotNull(message = "O ID do insumo é obrigatório")
        @Schema(description = "ID do insumo", example = "660e8400-e29b-41d4-a716-446655440111")
        UUID insumoId,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        @Schema(description = "Quantidade do insumo na ficha técnica", example = "5")
        Double quantidade
) {}
