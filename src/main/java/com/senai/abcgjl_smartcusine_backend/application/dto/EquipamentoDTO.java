package com.senai.abcgjl_smartcusine_backend.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EquipamentoDTO (


        @Schema(description = "ID do equipamento", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
        UUID idEquipamento,

    @NotBlank(message = "O tipo do equipamento não pode estar vazio")
    @Schema(description = "Tipo do equipamento", example = "Forno", required = true)
    String tipo,

    @NotNull(message = "A temperatura atual não pode ser nula")
        @Schema(description = "Temperatura atual do equipamento", example = "180.0", required = true)
        Double temperaturaAtual,

    @NotNull(message = "A temperatura ideal não pode ser nula")
        @Schema(description = "Temperatura ideal do equipamento", example = "200.0", required = true )
        Double temperaturaIdeal

) {

}
