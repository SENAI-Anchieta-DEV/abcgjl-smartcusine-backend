package com.senai.abcgjl_smartcusine_backend.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FichaTecnicaRequestDTO {

    @NotBlank(message = "O nome do preparo não pode estar vazio")
    private String nomePreparo;

    @NotBlank(message = "O tempo ideal não pode estar vazio")
    private String tempoIdeal;

    @NotNull(message = "A temperatura ideal não pode ser nula")
    @Positive(message = "A temperatura ideal deve ser positiva")
    private Double temperaturaIdeal;
}