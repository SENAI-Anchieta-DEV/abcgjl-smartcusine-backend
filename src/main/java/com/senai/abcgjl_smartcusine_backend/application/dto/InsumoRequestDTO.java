package com.senai.abcgjl_smartcusine_backend.application.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record InsumoRequestDTO (

        @NotBlank(message = "O nome do insumo não pode estar vazio")
        String nome,

        @NotBlank(message = "A unidade de medida não pode estar vazia")
        String unidadeMedida,

        @NotNull(message = "A quantidade em estoque não pode ser nula")
        @PositiveOrZero(message = "A quantidade em estoque deve ser zero ou positiva")
        Double quantidadeEstoque,

        @NotNull(message = "A data de validade não pode ser nula")
        @FutureOrPresent(message = "A data de validade deve ser hoje ou no futuro")
        LocalDate dataValidade
) {}



