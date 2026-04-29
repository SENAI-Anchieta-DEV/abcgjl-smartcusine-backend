package com.senai.abcgjl_smartcusine_backend.unit.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.UsuarioRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.UsuarioService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.UsuarioEntity;
import com.senai.abcgjl_smartcusine_backend.domain.enums.TipoUsuario;
import com.senai.abcgjl_smartcusine_backend.domain.exception.EmailJaCadastradoException;
import com.senai.abcgjl_smartcusine_backend.domain.exception.UsuarioNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.UsuarioRepository;
import com.senai.abcgjl_smartcusine_backend.interfaces.exception.AcessoNegadoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder encoder;
    private UsuarioService usuarioService;
    private Validator validator;


    @BeforeEach
    void setup() {
        usuarioRepository = mock(UsuarioRepository.class);
        encoder = mock(PasswordEncoder.class);
        usuarioService = new UsuarioService(usuarioRepository, encoder);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    //  O MÉTODO HELPER
    private void mockUsuarioLogado(String email, TipoUsuario tipo) {
        SecurityContext context = mock(SecurityContext.class);
        Authentication auth = mock(Authentication.class);

        when(context.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn(email);

        SecurityContextHolder.setContext(context);
    }

    @Test
    void deveFalharQuandoNomeForVazio() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNome("");
        dto.setEmail("teste@email.com");
        dto.setSenha("123456");
        dto.setTipo(TipoUsuario.ADMIN);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deveFalharQuandoEmailInvalido() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNome("João");
        dto.setEmail("email-invalido");
        dto.setSenha("123456");
        dto.setTipo(TipoUsuario.ADMIN);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    // =========================
    // CADASTRO
    // =========================

    @Test
    void deveCadastrarUsuarioComSucesso() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setEmail("teste@email.com");
        dto.setSenha("123456");
        dto.setTipo(TipoUsuario.ADMIN);

        when(usuarioRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(encoder.encode(any())).thenReturn("senhaCriptografada");
        when(usuarioRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        assertDoesNotThrow(() -> usuarioService.cadastrarUsuario(dto));

        verify(usuarioRepository).save(any()); // ✅ ADICIONADO (boa prática)
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExiste() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setEmail("teste@email.com");
        dto.setSenha("123456");
        dto.setTipo(TipoUsuario.ADMIN);

        when(usuarioRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.of(new UsuarioEntity()));

        assertThrows(EmailJaCadastradoException.class, () -> {
            usuarioService.cadastrarUsuario(dto);
        });
    }

    // =========================
    // BUSCA
    // =========================

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.buscarPorId(1L);
        });
    }

    @Test
    void deveBuscarUsuarioPorId() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> usuarioService.buscarPorId(1L));
    }

    // =========================
    // DELETE
    // =========================

    @Test
    void deveFalharAoDeletarUsuarioInexistente() {

        mockUsuarioLogado("admin@email.com", TipoUsuario.ADMIN); // ✅ ALTERADO

        UsuarioEntity admin = new UsuarioEntity();
        admin.setTipo(TipoUsuario.ADMIN);

        when(usuarioRepository.findByEmail("admin@email.com")) // ✅ ADICIONADO
                .thenReturn(Optional.of(admin));

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.deletarUsuario(1L);
        });
    }

    @Test
    void gerenteNaoPodeDeletarAdmin() {

        mockUsuarioLogado("gerente@email.com", TipoUsuario.GERENTE); // ✅ ALTERADO

        UsuarioEntity gerente = new UsuarioEntity();
        gerente.setTipo(TipoUsuario.GERENTE);

        UsuarioEntity admin = new UsuarioEntity();
        admin.setTipo(TipoUsuario.ADMIN);

        when(usuarioRepository.findByEmail("gerente@email.com")) // ✅ ALTERADO
                .thenReturn(Optional.of(gerente));

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(admin));

        assertThrows(AcessoNegadoException.class, () -> {
            usuarioService.deletarUsuario(1L);
        });
    }

    // =========================
    // LISTAR
    // =========================

    @Test
    void deveListarUsuarios() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1L);

        when(usuarioRepository.findAll())
                .thenReturn(java.util.List.of(usuario));

        assertDoesNotThrow(() -> usuarioService.listarUsuarios());
    }

    // =========================
    // UPDATE
    // =========================

    @Test
    void deveAtualizarUsuarioComSucesso() {

        mockUsuarioLogado("admin@email.com", TipoUsuario.ADMIN); // ✅ ALTERADO

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1L);
        usuario.setTipo(TipoUsuario.ADMIN);

        when(usuarioRepository.findByEmail("admin@email.com")) // ✅ ALTERADO
                .thenReturn(Optional.of(usuario));

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        when(encoder.encode(any()))
                .thenReturn("senhaCriptografada");

        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNome("Novo Nome");
        dto.setEmail("novo@email.com");
        dto.setSenha("123456");
        dto.setTipo(TipoUsuario.ADMIN);

        assertDoesNotThrow(() -> usuarioService.atualizarUsuario(1L, dto));

        verify(usuarioRepository).save(any()); // ✅ ADICIONADO
    }

    @Test
    void gerenteNaoPodeAtualizarAdmin() {

        mockUsuarioLogado("gerente@email.com", TipoUsuario.GERENTE); // ✅ ALTERADO

        UsuarioEntity gerente = new UsuarioEntity();
        gerente.setTipo(TipoUsuario.GERENTE);

        UsuarioEntity admin = new UsuarioEntity();
        admin.setTipo(TipoUsuario.ADMIN);

        when(usuarioRepository.findByEmail("gerente@email.com")) // ✅ ALTERADO
                .thenReturn(Optional.of(gerente));

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(admin));

        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setTipo(TipoUsuario.ADMIN);

        assertThrows(AcessoNegadoException.class, () -> {
            usuarioService.atualizarUsuario(1L, dto);
        });
    }
}