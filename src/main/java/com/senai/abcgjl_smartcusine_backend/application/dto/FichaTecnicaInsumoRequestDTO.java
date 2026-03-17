package com.senai.abcgjl_smartcusine_backend.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record FichaTecnicaInsumoRequestDTO(

        @NotNull(message = "O ID da ficha técnica é obrigatório")
        UUID fichaTecnicaId,

        @NotNull(message = "O ID do insumo é obrigatório")
        UUID insumoId,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Double quantidade
) {}
