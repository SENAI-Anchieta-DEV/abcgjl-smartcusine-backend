package com.senai.abcgjl_smartcusine_backend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.abcgjl_smartcusine_backend.domain.entity.AlertaEntity;
import com.senai.abcgjl_smartcusine_backend.domain.repository.AlertaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AlertaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private AlertaEntity alerta;

    @BeforeEach
    void setup() {

        alertaRepository.deleteAll();

        alerta = new AlertaEntity();
        alerta.setTipo("PERIGO");
        alerta.setMensagem("Temperatura alta");

        alerta = alertaRepository.save(alerta);
    }

    @Test
    @WithMockUser
    void deveListarAlertas() throws Exception {

        mockMvc.perform(get("/alerta"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveDeletarAlerta() throws Exception {

        mockMvc.perform(delete("/alerta/{id}", alerta.getIdAlerta()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void naoDeveDeletarSemPermissaoAdmin() throws Exception {

        mockMvc.perform(delete("/alerta/{id}", alerta.getIdAlerta()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveRetornar404AoDeletarAlertaInexistente() throws Exception {

        mockMvc.perform(delete("/alerta/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}