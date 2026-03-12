package com.senai.abcgjl_smartcusine_backend.domain.exception;

public class DadosInvalidosException extends RuntimeException {
    public DadosInvalidosException() {
        super("Dados inválidos enviados pelo usuário.");
    }
}
