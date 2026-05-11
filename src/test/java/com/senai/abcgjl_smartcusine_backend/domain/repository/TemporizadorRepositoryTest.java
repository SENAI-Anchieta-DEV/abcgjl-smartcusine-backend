package com.senai.abcgjl_smartcusine_backend.domain.repository;

import com.senai.abcgjl_smartcusine_backend.domain.entity.TemporizadorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TemporizadorRepositoryTest {

    @Autowired
    private TemporizadorRepository temporizadorRepository;

    @Test
    void deveSalvarEBuscarTemporizadorPorId() {

        TemporizadorEntity temporizador = new TemporizadorEntity();

        temporizador.setTempoAtual(30);
        temporizador.setTempoConfigurado(60);

        TemporizadorEntity salvo = temporizadorRepository.save(temporizador);

        Optional<TemporizadorEntity> encontrado =
                temporizadorRepository.findById(salvo.getIdTemporizador());

        assertTrue(encontrado.isPresent());
        assertEquals(30, encontrado.get().getTempoAtual());
        assertEquals(60, encontrado.get().getTempoConfigurado());
    }
}