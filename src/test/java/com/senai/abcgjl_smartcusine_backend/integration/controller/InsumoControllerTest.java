package com.senai.abcgjl_smartcusine_backend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.abcgjl_smartcusine_backend.application.dto.InsumoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.InsumoResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.flyway.enabled=false",
        "security.jwt.secret=minhachavesecretamuitograndeecommaisde32caracteres123456789",
        "security.jwt.expiration=3600"
})
@AutoConfigureMockMvc(addFilters = false)
class InsumoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarInsumoComSucesso() throws Exception {
        InsumoRequestDTO request = new InsumoRequestDTO(
                "Açúcar",
                "kg",
                10.0,
                LocalDate.now().plusDays(10)
        );

        mockMvc.perform(post("/insumos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void naoDeveCriarInsumoComNomeVazio() throws Exception {
        InsumoRequestDTO request = new InsumoRequestDTO(
                "",
                "kg",
                10.0,
                LocalDate.now().plusDays(10)
        );

        mockMvc.perform(post("/insumos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarInsumoComUnidadeMedidaVazia() throws Exception {
        InsumoRequestDTO request = new InsumoRequestDTO(
                "Açúcar",
                "",
                10.0,
                LocalDate.now().plusDays(10)
        );

        mockMvc.perform(post("/insumos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarInsumoComQuantidadeNula() throws Exception {
        InsumoRequestDTO request = new InsumoRequestDTO(
                "Açúcar",
                "kg",
                null,
                LocalDate.now().plusDays(10)
        );

        mockMvc.perform(post("/insumos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarInsumoComQuantidadeNegativa() throws Exception {
        InsumoRequestDTO request = new InsumoRequestDTO(
                "Açúcar",
                "kg",
                -5.0,
                LocalDate.now().plusDays(10)
        );

        mockMvc.perform(post("/insumos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarInsumoComDataValidadeNula() throws Exception {
        InsumoRequestDTO request = new InsumoRequestDTO(
                "Açúcar",
                "kg",
                10.0,
                null
        );

        mockMvc.perform(post("/insumos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarInsumoComDataValidadePassada() throws Exception {
        InsumoRequestDTO request = new InsumoRequestDTO(
                "Açúcar",
                "kg",
                10.0,
                LocalDate.now().minusDays(1)
        );

        mockMvc.perform(post("/insumos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarNotFoundAoBuscarInsumoInexistente() throws Exception {
        mockMvc.perform(get("/insumos/550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarInsumoComSucesso() throws Exception {
        InsumoRequestDTO request = new InsumoRequestDTO(
                "Farinha",
                "kg",
                20.0,
                LocalDate.now().plusDays(10)
        );

        String response = mockMvc.perform(post("/insumos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        InsumoResponseDTO criado = objectMapper.readValue(response, InsumoResponseDTO.class);

        InsumoRequestDTO update = new InsumoRequestDTO(
                "Farinha Atualizada",
                "kg",
                30.0,
                LocalDate.now().plusDays(20)
        );

        mockMvc.perform(put("/insumos/" + criado.idInsumo())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarNotFoundAoAtualizarInsumoInexistente() throws Exception {
        InsumoRequestDTO update = new InsumoRequestDTO(
                "Farinha Atualizada",
                "kg",
                30.0,
                LocalDate.now().plusDays(20)
        );

        mockMvc.perform(put("/insumos/550e8400-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarInsumoComSucesso() throws Exception {
        InsumoRequestDTO request = new InsumoRequestDTO(
                "Sal",
                "kg",
                5.0,
                LocalDate.now().plusDays(10)
        );

        String response = mockMvc.perform(post("/insumos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        InsumoResponseDTO criado = objectMapper.readValue(response, InsumoResponseDTO.class);

        mockMvc.perform(delete("/insumos/" + criado.idInsumo()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoDeletarInsumoInexistente() throws Exception {
        mockMvc.perform(delete("/insumos/550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(status().isNotFound());
    }
}