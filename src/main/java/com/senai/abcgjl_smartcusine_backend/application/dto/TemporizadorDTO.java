package com.senai.abcgjl_smartcusine_backend.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record TemporizadorDTO(
        @Schema(description = "ID do temporizador", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
        UUID idTemporizador,

        @NotNull(message = "O tempo configurado não pode ser nulo")
        @Positive(message = "O tempo configurado deve ser positivo")
        @Schema(description = "Tempo configurado para o temporizador em minutos", example = "30", required = true)
        Integer tempoConfigurado,

        @NotNull(message = "O tempo atual não pode ser nulo")
        @Positive(message = "O tempo atual deve ser positivo")
        @Schema(description = "Tempo atual do temporizador em minutos", example = "15", required = true)
        Integer tempoAtual,

        @Schema(description = "ID do equipamento associado", example = "3fa85f64-5717-4562-b3fc-2c963f66afa7", required = true)
        UUID equipamentoId
) {
}
