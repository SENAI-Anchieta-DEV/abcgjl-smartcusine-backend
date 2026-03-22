package com.senai.abcgjl_smartcusine_backend.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FichaTecnicaRequestDTO {

    @NotBlank(message = "O nome do preparo não pode estar vazio")
    @Schema(description = "Nome do preparo", example = "Bolo de Chocolate", required = true)
    private String nomePreparo;

    @NotBlank(message = "O tempo ideal não pode estar vazio")
    @Schema(description = "Tempo ideal de preparo", example = "30 minutos", required = true)
    private String tempoIdeal;

    @NotNull(message = "A temperatura ideal não pode ser nula")
    @Positive(message = "A temperatura ideal deve ser positiva")
    @Schema(description = "Temperatura ideal de preparo em °C", example = "180", required = true)
    private Double temperaturaIdeal;
}