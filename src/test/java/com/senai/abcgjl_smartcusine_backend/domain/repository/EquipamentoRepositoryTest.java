package com.senai.abcgjl_smartcusine_backend.domain.repository;

import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class EquipamentoRepositoryTest {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Test
    void deveSalvarEBuscarEquipamentoPorId() {
        EquipamentoEntity equipamento = new EquipamentoEntity();
        equipamento.setTipo("Forno Teste");
        equipamento.setTemperaturaAtual(180.0);
        equipamento.setTemperaturaIdeal(200.0);

        EquipamentoEntity salvo = equipamentoRepository.save(equipamento);

        Optional<EquipamentoEntity> encontrado =
                equipamentoRepository.findById(salvo.getIdEquipamento());

        assertTrue(encontrado.isPresent());
        assertEquals("Forno Teste", encontrado.get().getTipo());
        assertEquals(180.0, encontrado.get().getTemperaturaAtual());
        assertEquals(200.0, encontrado.get().getTemperaturaIdeal());
    }
}