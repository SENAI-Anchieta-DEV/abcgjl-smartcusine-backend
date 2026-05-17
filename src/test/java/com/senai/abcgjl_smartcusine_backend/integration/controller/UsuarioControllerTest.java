package com.senai.abcgjl_smartcusine_backend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.abcgjl_smartcusine_backend.domain.entity.UsuarioEntity;
import com.senai.abcgjl_smartcusine_backend.domain.enums.TipoUsuario;
import com.senai.abcgjl_smartcusine_backend.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.flyway.enabled=false",
        "security.jwt.secret=minhachavesecretamuitograndeecommaisde32caracteres123456789",
        "security.jwt.expiration=3600"
})
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioEntity usuario;

    @BeforeEach
    void setup() {

        usuarioRepository.deleteAll();

        usuario = new UsuarioEntity();

        usuario.setNome("Administrador");
        usuario.setEmail("admin@email.com");
        usuario.setSenha("123456");
        usuario.setTipo(TipoUsuario.ADMIN);

        usuario = usuarioRepository.save(usuario);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve listar usuários")
    void deveListarUsuarios() throws Exception {

        mockMvc.perform(
                        get("/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve retornar 404 ao deletar usuário inexistente")
    void deveRetornar404AoDeletarUsuarioInexistente() throws Exception {

        mockMvc.perform(
                        delete("/usuarios/{id}", 999999L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }
}