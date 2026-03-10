package com.senai.abcgjl_smartcusine_backend.interfaces.exception;

import com.senai.abcgjl_smartcusine_backend.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DadosInvalidosException.class)
    public ProblemDetail handlerDadosInvalidos (DadosInvalidosException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.BAD_REQUEST,
                "Dados inválidos enviados pelo usuário.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(UsuarioNaoAutenticadoException.class)
    public ProblemDetail handlerUsuarioNaoAutenticado (UsuarioNaoAutenticadoException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.UNAUTHORIZED,
                "Usuário não autenticado.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(UsuarioSemPermissaoException.class)
    public ProblemDetail handlerUsuarioSemPermissao (UsuarioSemPermissaoException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.FORBIDDEN,
                "Usuário sem permissão para a ação.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ProblemDetail handlerRecursoNaoEncontrado (RecursoNaoEncontradoException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ConflitoDeDadosException.class)
    public ProblemDetail handlerConflitoDeDados (ConflitoDeDadosException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.CONFLICT,
                "Conflito de dados (ex: insumo duplicado).",
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}
