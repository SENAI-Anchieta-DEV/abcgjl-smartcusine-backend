package com.senai.abcgjl_smartcusine_backend.domain.repository;
import org.springframework.test.context.ActiveProfiles;
import com.senai.abcgjl_smartcusine_backend.domain.InsumoRepository;
import com.senai.abcgjl_smartcusine_backend.domain.entity.InsumoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class InsumoRepositoryTest {


    @Autowired
    private InsumoRepository insumoRepository;

    @Test
    void deveSalvarEBuscarInsumoPorId() {
        InsumoEntity insumo = InsumoEntity.builder()
                .nome("Farinha Teste")
                .unidadeMedida("kg")
                .quantidadeEstoque(10.0)
                .dataValidade(LocalDate.now().plusDays(30))
                .qrCode("QR-TESTE")
                .build();

        InsumoEntity salvo = insumoRepository.save(insumo);

        Optional<InsumoEntity> encontrado = insumoRepository.findById(salvo.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Farinha Teste", encontrado.get().getNome());
        assertEquals("kg", encontrado.get().getUnidadeMedida());
    }

    @Test
    void deveVerificarSeExistePorNome() {
        InsumoEntity insumo = InsumoEntity.builder()
                .nome("Açúcar Teste")
                .unidadeMedida("kg")
                .quantidadeEstoque(5.0)
                .dataValidade(LocalDate.now().plusDays(20))
                .qrCode("QR-ACUCAR")
                .build();

        insumoRepository.save(insumo);

        boolean existe = insumoRepository.existsByNome("Açúcar Teste");

        assertTrue(existe);
    }
}
