package com.senai.abcgjl_smartcusine_backend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.flyway.enabled=false",
        "security.jwt.secret=minhachavesecretamuitograndeecommaisde32caracteres123456789",
        "security.jwt.expiration=3600"
})
@AutoConfigureMockMvc(addFilters = false)
class FichaTecnicaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveCadastrarFichaTecnica() throws Exception {

        String json = """
                {
                    "nomePreparo": "Lasanha",
                    "tempoIdeal": "40 minutos",
                    "temperaturaIdeal": 180.0
                }
                """;

        mockMvc.perform(post("/fichas-tecnicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void naoDeveCadastrarFichaTecnicaSemNome() throws Exception {

        String json = """
                {
                    "nomePreparo": "",
                    "tempoIdeal": "40 minutos",
                    "temperaturaIdeal": 180.0
                }
                """;

        mockMvc.perform(post("/fichas-tecnicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void naoDeveCadastrarFichaTecnicaComTemperaturaNegativa() throws Exception {

        String json = """
                {
                    "nomePreparo": "Lasanha",
                    "tempoIdeal": "40 minutos",
                    "temperaturaIdeal": -10.0
                }
                """;

        mockMvc.perform(post("/fichas-tecnicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}