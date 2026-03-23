package com.senai.abcgjl_smartcusine_backend.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FichaTecnicaResponseDTO {

    @Schema(description = "ID da ficha técnica", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
    private UUID idFicha;

    @Schema(description = "Nome do preparo", example = "Bolo de Chocolate", required = true)
    private String nomePreparo;

    @Schema(description = "Tempo ideal de preparo", example = "30 minutos", required = true)
    private String tempoIdeal;

    @Schema(description = "Temperatura ideal de preparo em °C", example = "180", required = true)
    private Double temperaturaIdeal;
}

