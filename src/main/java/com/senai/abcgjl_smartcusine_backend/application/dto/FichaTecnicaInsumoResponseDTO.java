package com.senai.abcgjl_smartcusine_backend.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FichaTecnicaInsumoResponseDTO {

    @Schema(description = "ID do insumo na ficha técnica", example = "770e8400-e29b-41d4-a716-446655440222", required = true)
    private UUID id;

    @NotBlank(message = "O nome do insumo não pode estar vazio")
    @Schema(description = "Nome do insumo", example = "Farinha de Trigo", required = true)
    private String nomeInsumo;

    @NotBlank(message = "O nome do preparo não pode estar vazio")
    @Schema(description = "Nome do preparo", example = "Bolo de Chocolate", required = true)
    private String nomePreparo;

    @NotNull(message = "A quantidade não pode ser nula")
    @Positive(message = "A quantidade deve ser maior que zero")
    @Schema(description = "Quantidade do insumo na ficha técnica", example = "5", required = true)
    private Double quantidade;
}
