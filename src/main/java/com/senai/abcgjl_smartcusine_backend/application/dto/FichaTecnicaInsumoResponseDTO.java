package com.senai.abcgjl_smartcusine_backend.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FichaTecnicaInsumoResponseDTO {

    private UUID id;
    private String nomeInsumo;
    private String nomePreparo;
    private Double quantidade;
}
