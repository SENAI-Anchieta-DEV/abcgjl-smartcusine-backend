package com.senai.abcgjl_smartcusine_backend.integration.controller;

import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import com.senai.abcgjl_smartcusine_backend.domain.entity.InsumoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.repository.FichaTecnicaRepository;
import com.senai.abcgjl_smartcusine_backend.domain.repository.InsumoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.flyway.enabled=false",
        "security.jwt.secret=minhachavesecretamuitograndeecommaisde32caracteres123456789",
        "security.jwt.expiration=3600"
})
@AutoConfigureMockMvc(addFilters = false)
class FichaTecnicaInsumoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FichaTecnicaRepository fichaTecnicaRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    @Test
    @WithMockUser
    void deveCadastrarFichaTecnicaInsumo() throws Exception {

        FichaTecnicaEntity fichaTecnica = FichaTecnicaEntity.builder()
                .nomePreparo("Ficha Teste")
                .build();

        fichaTecnica = fichaTecnicaRepository.save(fichaTecnica);

        InsumoEntity insumo = InsumoEntity.builder()
                .nome("Arroz")
                .unidadeMedida("KG")
                .quantidadeEstoque(10.0)
                .dataValidade(LocalDate.now().plusDays(30))
                .qrCode("123456789")
                .build();

        insumo = insumoRepository.save(insumo);

        String json = """
                {
                    "fichaTecnicaId": "%s",
                    "insumoId": "%s",
                    "quantidade": 2.0,
                    "unidade": "KG"
                }
                """.formatted(
                fichaTecnica.getId(),
                insumo.getId()
        );

        mockMvc.perform(post("/ficha-tecnica-insumos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void naoDeveCadastrarComQuantidadeNegativa() throws Exception {

        FichaTecnicaEntity fichaTecnica = FichaTecnicaEntity.builder()
                .nomePreparo("Ficha Teste")
                .build();

        fichaTecnica = fichaTecnicaRepository.save(fichaTecnica);

        InsumoEntity insumo = InsumoEntity.builder()
                .nome("Arroz")
                .unidadeMedida("KG")
                .quantidadeEstoque(10.0)
                .dataValidade(LocalDate.now().plusDays(30))
                .qrCode("123456789")
                .build();

        insumo = insumoRepository.save(insumo);

        String json = """
                {
                    "fichaTecnicaId": "%s",
                    "insumoId": "%s",
                    "quantidade": -2.0,
                    "unidade": "KG"
                }
                """.formatted(
                fichaTecnica.getId(),
                insumo.getId()
        );

        mockMvc.perform(post("/ficha-tecnica-insumos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void naoDeveCadastrarComFichaTecnicaInexistente() {

        Assertions.assertThrows(Exception.class, () -> {

            InsumoEntity insumo = InsumoEntity.builder()
                    .nome("Arroz")
                    .unidadeMedida("KG")
                    .quantidadeEstoque(10.0)
                    .dataValidade(LocalDate.now().plusDays(30))
                    .qrCode("123456789")
                    .build();

            insumo = insumoRepository.save(insumo);

            String json = """
                    {
                        "fichaTecnicaId": "550e8400-e29b-41d4-a716-446655440000",
                        "insumoId": "%s",
                        "quantidade": 2.0,
                        "unidade": "KG"
                    }
                    """.formatted(insumo.getId());

            mockMvc.perform(post("/ficha-tecnica-insumos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json));

        });
    }
}