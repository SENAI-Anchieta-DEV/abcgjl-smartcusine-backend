package com.senai.abcgjl_smartcusine_backend.domain.exception;

public class UsuarioNaoAutenticadoException extends RuntimeException {
    public UsuarioNaoAutenticadoException() {
        super("Usuário não autenticado.");
    }
}
