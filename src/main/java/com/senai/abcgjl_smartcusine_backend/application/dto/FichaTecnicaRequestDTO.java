package com.senai.abcgjl_smartcusine_backend.application.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FichaTecnicaRequestDTO {

    private String nomePreparo;
    private String tempoIdeal;
    private Double temperaturaIdeal;

}
