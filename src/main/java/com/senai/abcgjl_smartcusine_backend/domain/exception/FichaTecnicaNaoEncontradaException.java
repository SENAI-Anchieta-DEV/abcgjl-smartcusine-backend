package com.senai.abcgjl_smartcusine_backend.domain.exception;

public class FichaTecnicaNaoEncontradaException extends RuntimeException {
    public FichaTecnicaNaoEncontradaException() {
        super("Ficha técnica não encontrada");
    }
}
