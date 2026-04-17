package com.senai.abcgjl_smartcusine_backend;

import com.senai.abcgjl_smartcusine_backend.application.dto.UsuarioRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.UsuarioService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.UsuarioEntity;
import com.senai.abcgjl_smartcusine_backend.domain.enums.TipoUsuario;
import com.senai.abcgjl_smartcusine_backend.domain.exception.EmailJaCadastradoException;
import com.senai.abcgjl_smartcusine_backend.domain.exception.UsuarioNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.UsuarioRepository;
import com.senai.abcgjl_smartcusine_backend.interfaces.exception.AcessoNegadoException;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    //  O MÉTODO HELPER
    private void mockUsuarioLogado() {
        SecurityContext context = mock(SecurityContext.class);
        Authentication auth = mock(Authentication.class);

        when(context.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("admin@email.com");

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
    void deveFalharQuandoNomeForPequeno() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNome("Jo");
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

    @Test
    void deveFalharQuandoSenhaForCurta() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNome("João");
        dto.setEmail("joao@email.com");
        dto.setSenha("123");
        dto.setTipo(TipoUsuario.ADMIN);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deveFalharQuandoTipoForNulo() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNome("João");
        dto.setEmail("joao@email.com");
        dto.setSenha("123456");
        dto.setTipo(null);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    // CADASTRAR USUÁRIO
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
    }

    // ❌ EMAIL DUPLICADO
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

    // ❌ SENHA CURTA
    @Test
    void deveLancarErroQuandoSenhaMenorQue6() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setEmail("teste@email.com");
        dto.setSenha("123");
        dto.setTipo(TipoUsuario.ADMIN);

        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.cadastrarUsuario(dto);
        });
    }

    // ❌ TIPO NULO
    @Test
    void deveLancarErroQuandoTipoNulo() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setEmail("teste@email.com");
        dto.setSenha("123456");
        dto.setTipo(null);

        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.cadastrarUsuario(dto);
        });
    }

    // ❌ USUÁRIO NÃO ENCONTRADO
    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.buscarPorId(1L);
        });
    }

    // ✅ BUSCAR USUÁRIO
    @Test
    void deveBuscarUsuarioPorId() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> usuarioService.buscarPorId(1L));
    }

    // ❌ DELETAR USUÁRIO NÃO EXISTE
    @Test
    void deveFalharAoDeletarUsuarioInexistente() {
        mockUsuarioLogado();

        when(usuarioRepository.findByEmail(any()))
                .thenReturn(Optional.of(new UsuarioEntity()));

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.deletarUsuario(1L);
        });

        SecurityContextHolder.clearContext();
    }

    // ❌ REGRA: GERENTE NÃO PODE DELETAR ADMIN
    @Test
    void gerenteNaoPodeDeletarAdmin() {

        SecurityContext context = mock(SecurityContext.class);
        Authentication auth = mock(Authentication.class);

        when(context.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("gerente@email.com");

        SecurityContextHolder.setContext(context);

        UsuarioEntity gerente = new UsuarioEntity();
        gerente.setTipo(TipoUsuario.GERENTE);

        UsuarioEntity admin = new UsuarioEntity();
        admin.setTipo(TipoUsuario.ADMIN);

        when(usuarioRepository.findByEmail(any()))
                .thenReturn(Optional.of(gerente));

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(admin));

        // mock do contexto de segurança seria necessário aqui (avançado)

        assertThrows(AcessoNegadoException.class, () -> {
            usuarioService.deletarUsuario(1L);
        });

    }
    @Test
    void deveListarUsuarios () {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1L);

        when(usuarioRepository.findAll())
                .thenReturn(java.util.List.of(usuario));

        assertDoesNotThrow(() -> usuarioService.listarUsuarios());
    }

    // 🔽 ADICIONAR - ATUALIZAR USUÁRIO (SUCESSO)


    @Test
    void deveAtualizarUsuarioComSucesso() {
        mockUsuarioLogado();

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1L);
        usuario.setTipo(TipoUsuario.ADMIN);

        when(usuarioRepository.findByEmail(any()))
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

        SecurityContextHolder.clearContext();
    }

    // GERENTE NÃO PODE ATUALIZAR ADMIN


    @Test
    void gerenteNaoPodeAtualizarAdmin() {
        mockUsuarioLogado();

        UsuarioEntity gerente = new UsuarioEntity();
        gerente.setTipo(TipoUsuario.GERENTE);

        UsuarioEntity admin = new UsuarioEntity();
        admin.setTipo(TipoUsuario.ADMIN);

        when(usuarioRepository.findByEmail(any()))
                .thenReturn(Optional.of(gerente));

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(admin));

        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setTipo(TipoUsuario.ADMIN);

        assertThrows(AcessoNegadoException.class, () -> {
            usuarioService.atualizarUsuario(1L, dto);
        });

        SecurityContextHolder.clearContext();
    }
}


