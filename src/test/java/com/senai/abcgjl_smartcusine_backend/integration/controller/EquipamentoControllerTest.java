package com.senai.abcgjl_smartcusine_backend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import com.senai.abcgjl_smartcusine_backend.domain.repository.FichaTecnicaRepository;
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
class EquipamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FichaTecnicaRepository fichaTecnicaRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveCadastrarEquipamento() throws Exception {

        FichaTecnicaEntity fichaTecnica = FichaTecnicaEntity.builder()
                .nomePreparo("Lasanha")
                .tempoIdeal("40 minutos")
                .temperaturaIdeal(180.0)
                .build();

        fichaTecnica = fichaTecnicaRepository.save(fichaTecnica);

        String json = """
                {
                    "tipo": "Forno",
                    "temperaturaAtual": 170.0,
                    "temperaturaIdeal": 180.0,
                    "fichaTecnicaId": "%s"
                }
                """.formatted(fichaTecnica.getId());

        mockMvc.perform(post("/equipamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void naoDeveCadastrarEquipamentoSemTipo() throws Exception {

        FichaTecnicaEntity fichaTecnica = FichaTecnicaEntity.builder()
                .nomePreparo("Lasanha")
                .tempoIdeal("40 minutos")
                .temperaturaIdeal(180.0)
                .build();

        fichaTecnica = fichaTecnicaRepository.save(fichaTecnica);

        String json = """
                {
                    "tipo": "",
                    "temperaturaAtual": 170.0,
                    "temperaturaIdeal": 180.0,
                    "fichaTecnicaId": "%s"
                }
                """.formatted(fichaTecnica.getId());

        mockMvc.perform(post("/equipamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void naoDeveCadastrarEquipamentoComFichaTecnicaInexistente() throws Exception {

        String json = """
                {
                    "tipo": "Forno",
                    "temperaturaAtual": 170.0,
                    "temperaturaIdeal": 180.0,
                    "fichaTecnicaId": "550e8400-e29b-41d4-a716-446655440000"
                }
                """;

        mockMvc.perform(post("/equipamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }
}