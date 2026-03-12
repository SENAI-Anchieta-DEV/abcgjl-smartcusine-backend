package com.senai.abcgjl_smartcusine_backend.domain.exception;

public class InsumoNaoEncontradoException extends RuntimeException {
    public InsumoNaoEncontradoException() {
        super("Insumo não encontrado.");
    }
}
