package com.senai.abcgjl_smartcusine_backend.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record InsumoRequestDTO (

        @NotBlank(message = "O nome do insumo não pode estar vazio")
        @Schema(description = "Nome do insumo", example = "Açúcar")
        String nome,

        @NotBlank(message = "A unidade de medida não pode estar vazia")
        @Schema(description = "Unidade de medida do insumo", example = "kg")
        String unidadeMedida,

        @NotNull(message = "A quantidade em estoque não pode ser nula")
        @PositiveOrZero(message = "A quantidade em estoque deve ser zero ou positiva")
        @Schema(description = "Quantidade em estoque", example = "12.5")
        Double quantidadeEstoque,

        @NotNull(message = "A data de validade não pode ser nula")
        @FutureOrPresent(message = "A data de validade deve ser hoje ou no futuro")
        @Schema(description = "Data de validade do insumo", example = "2026-12-31")
        LocalDate dataValidade
) {}



