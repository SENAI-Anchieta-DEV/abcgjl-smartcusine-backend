package com.senai.abcgjl_smartcusine_backend.application.dto;

import java.util.UUID;

public record FichaTecnicaInsumoRequestDTO(

        UUID fichaTecnicaId,
        UUID insumoId,
        Double quantidade
) {}
