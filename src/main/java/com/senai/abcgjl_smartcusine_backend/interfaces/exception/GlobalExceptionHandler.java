package com.senai.abcgjl_smartcusine_backend.interfaces.exception;

import com.senai.abcgjl_smartcusine_backend.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AcessoNegadoException.class)
    public ProblemDetail handlerAcessoNegado(AcessoNegadoException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.FORBIDDEN,
                "Acesso negado.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

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

    @ExceptionHandler(InsumoNaoEncontradoException.class)
    public ProblemDetail handlerRecursoNaoEncontrado (InsumoNaoEncontradoException ex, HttpServletRequest request){
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

    @ExceptionHandler(AlertaNaoEncontradoException.class)
    public ProblemDetail handlerAlertaNaoEncontrado (AlertaNaoEncontradoException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.NOT_FOUND,
                "Alerta não encontrado.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ProblemDetail handlerCredenciaisInvalidas (CredenciaisInvalidasException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.UNAUTHORIZED,
                "Credenciais invalidas.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ProblemDetail handlerEmailJaCadastrado (EmailJaCadastradoException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.CONFLICT,
                "Email ja cadastrado.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(EquipamentoNaoEncontradoException.class)
    public ProblemDetail handlerEquipamentoNaoEncontrado (EquipamentoNaoEncontradoException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.NOT_FOUND,
                "Equipamento não encontrado.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(FichaTecnicaNaoEncontradaException.class)
    public ProblemDetail handlerFichaTecnicaNaoEncontrada (FichaTecnicaNaoEncontradaException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.NOT_FOUND,
                "Ficha técnica não encontrada.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ProblemDetail handlerUsuarioNaoEncontrado (UsuarioNaoEncontradoException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.NOT_FOUND,
                "Usuário não encontrado.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(TemporizadorNaoEncontradoException.class)
    public ProblemDetail handlerTemporizadorNaoEncontrado (TemporizadorNaoEncontradoException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.NOT_FOUND,
                "Temporizador não encontrado.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}
