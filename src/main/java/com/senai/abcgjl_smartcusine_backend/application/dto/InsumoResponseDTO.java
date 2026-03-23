package com.senai.abcgjl_smartcusine_backend.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

public record InsumoResponseDTO(

        @Schema(description = "ID único do insumo", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID idInsumo,

        @Schema(description = "Nome do insumo", example = "Açúcar")
        String nome,

        @Schema(description = "Unidade de medida do insumo", example = "kg")
        String unidadeMedida,
        @Schema(description = "Quantidade em estoque", example = "12.5")
        Double quantidadeEstoque,
        @Schema(description = "Data de validade do insumo", example = "2026-12-31")
        LocalDate dataValidade,
        @Schema(description = "QR Code do insumo", example = "1234567890")
        String qrCode,
        @Schema(description = "Indica se o insumo está próximo da validade", example = "true")
        boolean alertaValidade
)
{}
