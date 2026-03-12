package com.senai.abcgjl_smartcusine_backend.application.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.AuthDTO;
import com.senai.abcgjl_smartcusine_backend.domain.entity.UsuarioEntity;
import com.senai.abcgjl_smartcusine_backend.domain.exception.CredenciaisInvalidasException;
import com.senai.abcgjl_smartcusine_backend.domain.exception.UsuarioNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.UsuarioRepository;
import com.senai.abcgjl_smartcusine_backend.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public String login(AuthDTO.LoginRequest req) {
        UsuarioEntity usuario = usuarios.findByEmail(req.email())
                .orElseThrow(() ->  new UsuarioNaoEncontradoException(/*"Usuário não foi encontrado"*/));

        if (!encoder.matches(req.senha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException();
        }

        return jwt.generateToken(usuario.getEmail(), usuario.getTipo().name());
    }
}
