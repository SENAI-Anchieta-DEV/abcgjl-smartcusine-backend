package com.senai.abcgjl_smartcusine_backend.domain.exception;

public class AlertaNaoEncontradoException extends RuntimeException {
    public AlertaNaoEncontradoException() {
        super("Alerta não encontrado");
    }
}
