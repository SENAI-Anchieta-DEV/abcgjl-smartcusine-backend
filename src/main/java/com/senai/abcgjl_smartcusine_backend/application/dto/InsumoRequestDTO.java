package com.senai.abcgjl_smartcusine_backend.application.dto;

import java.time.LocalDate;

public record InsumoRequestDTO (

        String nome,
        String unidadeMedida,
        Double quantidadeEstoque,
        LocalDate dataValidade
) {}



