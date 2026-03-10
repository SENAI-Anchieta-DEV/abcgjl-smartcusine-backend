package com.senai.abcgjl_smartcusine_backend.domain.exception;

public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException() {
        super("Recurso não encontrado.");
    }
}
