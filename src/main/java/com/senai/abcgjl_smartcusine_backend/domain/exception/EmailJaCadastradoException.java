package com.senai.abcgjl_smartcusine_backend.domain.exception;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException() {
        super("Email ja cadastrado");
    }
}
