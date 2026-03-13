package com.senai.abcgjl_smartcusine_backend.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FichaTecnicaInsumoResponseDTO {

    private UUID id;

    @NotBlank(message = "O nome do insumo não pode estar vazio")
    private String nomeInsumo;

    @NotBlank(message = "O nome do preparo não pode estar vazio")
    private String nomePreparo;

    @NotNull(message = "A quantidade não pode ser nula")
    @Positive(message = "A quantidade deve ser maior que zero")
    private Double quantidade;
}
