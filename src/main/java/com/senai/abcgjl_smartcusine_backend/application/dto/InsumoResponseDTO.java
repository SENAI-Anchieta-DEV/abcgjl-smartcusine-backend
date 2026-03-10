package com.senai.abcgjl_smartcusine_backend.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public record InsumoResponseDTO(

        UUID idInsumo,
        String nome,
        String unidadeMedida,
        Double quantidadeEstoque,
        LocalDate dataValidade,
        String qrCode,
        boolean alertaValidade
)
{}
