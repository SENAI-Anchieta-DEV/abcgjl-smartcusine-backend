/*package com.senai.abcgjl_smartcusine_backend;
import com.senai.abcgjl_smartcusine_backend.application.dto.AuthDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.AuthService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.UsuarioEntity;
import com.senai.abcgjl_smartcusine_backend.domain.exception.CredenciaisInvalidasException;
import com.senai.abcgjl_smartcusine_backend.domain.exception.UsuarioNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.UsuarioRepository;
import com.senai.abcgjl_smartcusine_backend.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class AuthServiceTest {
    private UsuarioRepository usuarios;
    private PasswordEncoder encoder;
    private JwtService jwt;
    private AuthService service;

    @BeforeEach
    void setup() {
        usuarios = mock(UsuarioRepository.class);
        encoder = mock(PasswordEncoder.class);
        jwt = mock(JwtService.class);

        service = new AuthService(usuarios, encoder, jwt);
    }

    // ❌ USUÁRIO NÃO EXISTE
    @Test
    void deveFalharQuandoUsuarioNaoExiste() {
        AuthDTO.LoginRequest req = mock(AuthDTO.LoginRequest.class);

        when(req.email()).thenReturn("teste@email.com");
        when(req.senha()).thenReturn("123");

        when(usuarios.findByEmail("teste@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> {
            service.login(req);
        });
    }

    // ❌ SENHA INVÁLIDA
    @Test
    void deveFalharQuandoSenhaIncorreta() {
        AuthDTO.LoginRequest req = mock(AuthDTO.LoginRequest.class);

        UsuarioEntity usuario = new UsuarioEntity();

        when(req.email()).thenReturn("teste@email.com");
        when(req.senha()).thenReturn("123");

        when(usuarios.findByEmail("teste@email.com"))
                .thenReturn(Optional.of(usuario));

        when(encoder.matches(any(), any())).thenReturn(false);

        assertThrows(CredenciaisInvalidasException.class, () -> {
            service.login(req);
        });
    }

    // ✅ LOGIN COM SUCESSO
    @Test
    void deveFazerLoginComSucesso() {
        AuthDTO.LoginRequest req = mock(AuthDTO.LoginRequest.class);

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setSenha("senhaCriptografada");

        when(req.email()).thenReturn("teste@email.com");
        when(req.senha()).thenReturn("123");

        when(usuarios.findByEmail("teste@email.com"))
                .thenReturn(Optional.of(usuario));

        when(encoder.matches("123", "senhaCriptografada"))
                .thenReturn(true);

        when(jwt.generateToken(usuario))
                .thenReturn("token-jwt");

        String token = service.login(req);

        assertNotNull(token);
        assertEquals("token-jwt", token);
    }
}*/
