package com.senai.abcgjl_smartcusine_backend.domain.repository;

import com.senai.abcgjl_smartcusine_backend.domain.AlertaRepository;
import com.senai.abcgjl_smartcusine_backend.domain.entity.AlertaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class AlertaRepositoryTest {

    @Autowired
    private AlertaRepository alertaRepository;

    @Test
    void deveSalvarEBuscarAlertaPorId() {

        AlertaEntity alerta = new AlertaEntity();

        alerta.setTipo("TEMPERATURA");
        alerta.setMensagem("Temperatura fora do padrão");

        AlertaEntity salvo = alertaRepository.save(alerta);

        Optional<AlertaEntity> encontrado =
                alertaRepository.findById(salvo.getIdAlerta());

        assertTrue(encontrado.isPresent());
        assertEquals("TEMPERATURA", encontrado.get().getTipo());
        assertEquals("Temperatura fora do padrão", encontrado.get().getMensagem());
    }
}