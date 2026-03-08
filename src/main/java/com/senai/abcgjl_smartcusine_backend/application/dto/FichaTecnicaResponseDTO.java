package com.senai.abcgjl_smartcusine_backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FichaTecnicaResponseDTO {

    private UUID idFicha;
    private String nomePreparo;
    private String tempoIdeal;
    private Double temperaturaIdeal;

}
