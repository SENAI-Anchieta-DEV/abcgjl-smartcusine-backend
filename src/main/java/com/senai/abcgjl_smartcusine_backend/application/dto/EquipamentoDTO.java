package com.senai.abcgjl_smartcusine_backend.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EquipamentoDTO (


    UUID idEquipamento,

    @NotBlank(message = "O tipo do equipamento não pode estar vazio")
    String tipo,

    @NotNull(message = "A temperatura atual não pode ser nula")
    Double temperaturaAtual,

    @NotNull(message = "A temperatura ideal não pode ser nula")
    Double temperaturaIdeal

) {

}
