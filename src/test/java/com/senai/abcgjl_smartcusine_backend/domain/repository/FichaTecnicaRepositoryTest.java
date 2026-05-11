package com.senai.abcgjl_smartcusine_backend.domain.repository;

import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class FichaTecnicaRepositoryTest {

    @Autowired
    private FichaTecnicaRepository fichaTecnicaRepository;

    @Test
    void deveSalvarEBuscarFichaTecnicaPorId() {

        FichaTecnicaEntity ficha = new FichaTecnicaEntity();

        ficha.setNomePreparo("Lasanha Teste");
        ficha.setTempoIdeal("45 minutos");
        ficha.setTemperaturaIdeal(180.0);

        FichaTecnicaEntity salvo = fichaTecnicaRepository.save(ficha);

        Optional<FichaTecnicaEntity> encontrado =
                fichaTecnicaRepository.findById(salvo.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Lasanha Teste", encontrado.get().getNomePreparo());
        assertEquals("45 minutos", encontrado.get().getTempoIdeal());
        assertEquals(180.0, encontrado.get().getTemperaturaIdeal());
    }
}