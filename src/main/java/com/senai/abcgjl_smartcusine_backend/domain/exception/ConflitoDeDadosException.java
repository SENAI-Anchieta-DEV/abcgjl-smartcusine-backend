package com.senai.abcgjl_smartcusine_backend.domain.exception;

public class ConflitoDeDadosException extends RuntimeException {
    public ConflitoDeDadosException() {
        super("Conflito de dados (ex: insumo duplicado).");
    }
}
