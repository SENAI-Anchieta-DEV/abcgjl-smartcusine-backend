package com.senai.abcgjl_smartcusine_backend.domain.exception;

public class TemporizadorNaoEncontradoException extends RuntimeException {
    public TemporizadorNaoEncontradoException() {
        super("temporizador não encontrado");
    }
}
