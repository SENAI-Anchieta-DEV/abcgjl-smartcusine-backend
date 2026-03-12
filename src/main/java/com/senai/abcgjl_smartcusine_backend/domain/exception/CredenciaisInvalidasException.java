package com.senai.abcgjl_smartcusine_backend.domain.exception;

public class CredenciaisInvalidasException extends RuntimeException {
    public CredenciaisInvalidasException() {
        super("Credenciais invalidas.");
    }
}
