package com.senai.abcgjl_smartcusine_backend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.abcgjl_smartcusine_backend.application.dto.TemporizadorDTO;
import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.repository.EquipamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.flyway.enabled=false",
        "security.jwt.secret=minhachavesecretamuitograndeecommaisde32caracteres123456789",
        "security.jwt.expiration=3600"
})
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser
class TemporizadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    private UUID equipamentoId;

    @BeforeEach
    void setup() {

        EquipamentoEntity equipamento = new EquipamentoEntity();

        /*
           ALTERE os campos abaixo conforme
           sua EquipamentoEntity exige
        */

        // exemplo:
        // equipamento.setNome("Forno");
        // equipamento.setStatus(true);

        EquipamentoEntity salvo = equipamentoRepository.save(equipamento);

        equipamentoId = salvo.getIdEquipamento();
    }

    @Test
    void deveCadastrarTemporizador() throws Exception {

        TemporizadorDTO dto = new TemporizadorDTO(
                null,
                30,
                15,
                equipamentoId
        );

        mockMvc.perform(post("/temporizadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tempoConfigurado").value(30))
                .andExpect(jsonPath("$.tempoAtual").value(15));
    }

    @Test
    void naoDeveCadastrarTemporizadorComTempoNegativo() throws Exception {

        TemporizadorDTO dto = new TemporizadorDTO(
                null,
                -30,
                15,
                equipamentoId
        );

        mockMvc.perform(post("/temporizadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCadastrarTemporizadorComEquipamentoInexistente() throws Exception {

        TemporizadorDTO dto = new TemporizadorDTO(
                null,
                30,
                15,
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000")
        );

        mockMvc.perform(post("/temporizadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}