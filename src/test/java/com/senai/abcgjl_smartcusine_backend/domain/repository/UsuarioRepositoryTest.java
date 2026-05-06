package com.senai.abcgjl_smartcusine_backend.domain.repository;

import com.senai.abcgjl_smartcusine_backend.domain.UsuarioRepository;
import com.senai.abcgjl_smartcusine_backend.domain.enums.TipoUsuario;
import com.senai.abcgjl_smartcusine_backend.domain.entity.UsuarioEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveSalvarEBuscarUsuarioPorEmail() {

        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setNome("Laura");
        usuario.setEmail("laura@email.com");
        usuario.setSenha("123456");
        usuario.setTipo(TipoUsuario.ADMIN);

        usuarioRepository.save(usuario);

        Optional<UsuarioEntity> encontrado =
                usuarioRepository.findByEmail("laura@email.com");

        assertTrue(encontrado.isPresent());
        assertEquals("Laura", encontrado.get().getNome());
        assertEquals(TipoUsuario.ADMIN, encontrado.get().getTipo());
    }
}