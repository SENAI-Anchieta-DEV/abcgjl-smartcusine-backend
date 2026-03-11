package com.senai.abcgjl_smartcusine_backend.domain.exception;

public class EquipamentoNaoEncontradoException extends RuntimeException {
    public EquipamentoNaoEncontradoException() {
        super("Equipamento não encontrado");
    }
}
