package com.senai.abcgjl_smartcusine_backend.domain.exception;

public class UsuarioSemPermissaoException extends RuntimeException {
    public UsuarioSemPermissaoException() {
        super("Usuário sem permissão para a ação.");
    }
}
