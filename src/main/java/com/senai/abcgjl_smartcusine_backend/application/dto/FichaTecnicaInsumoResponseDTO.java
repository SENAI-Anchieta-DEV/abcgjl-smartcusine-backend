package com.senai.abcgjl_smartcusine_backend.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FichaTecnicaInsumoResponseDTO {

    private String nomeInsumo;
    private String nomePreparo;
    private Double quantidade;
}
