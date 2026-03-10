package com.senai.abcgjl_smartcusine_backend.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record TemporizadorDTO(
        UUID idTemporizador,
        @NotNull(message = "O tempo configurado não pode ser nulo")
        @Positive(message = "O tempo configurado deve ser positivo")
        Integer tempoConfigurado,
        @NotNull(message = "O tempo atual não pode ser nulo")
        @Positive(message = "O tempo atual deve ser positivo")
        Integer tempoAtual,
        UUID equipamentoId
) {
}
