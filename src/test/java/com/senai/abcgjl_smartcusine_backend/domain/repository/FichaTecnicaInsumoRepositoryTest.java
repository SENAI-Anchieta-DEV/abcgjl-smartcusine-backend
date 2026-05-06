package com.senai.abcgjl_smartcusine_backend.domain.repository;

import com.senai.abcgjl_smartcusine_backend.domain.FichaTecnicaInsumoRepository;
import com.senai.abcgjl_smartcusine_backend.domain.FichaTecnicaRepository;
import com.senai.abcgjl_smartcusine_backend.domain.InsumoRepository;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaInsumoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.entity.InsumoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class FichaTecnicaInsumoRepositoryTest {

    @Autowired
    private FichaTecnicaRepository fichaTecnicaRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private FichaTecnicaInsumoRepository fichaTecnicaInsumoRepository;

    @Test
    void deveSalvarEBuscarFichaTecnicaInsumoPorId() {

        FichaTecnicaEntity ficha = new FichaTecnicaEntity();
        ficha.setNomePreparo("Bolo Teste");
        ficha.setTempoIdeal("40 minutos");
        ficha.setTemperaturaIdeal(180.0);

        FichaTecnicaEntity fichaSalva =
                fichaTecnicaRepository.save(ficha);

        InsumoEntity insumo = new InsumoEntity();
        insumo.setNome("Farinha");
        insumo.setUnidadeMedida("kg");
        insumo.setQuantidadeEstoque(10.0);
        insumo.setDataValidade(LocalDate.now().plusDays(30));
        insumo.setQrCode("QR-FARINHA");

        InsumoEntity insumoSalvo =
                insumoRepository.save(insumo);

        FichaTecnicaInsumoEntity fichaTecnicaInsumo =
                new FichaTecnicaInsumoEntity();

        fichaTecnicaInsumo.setFichaTecnica(fichaSalva);
        fichaTecnicaInsumo.setInsumo(insumoSalvo);
        fichaTecnicaInsumo.setQuantidade(2.0);
        fichaTecnicaInsumo.setUnidade("kg");

        FichaTecnicaInsumoEntity salvo =
                fichaTecnicaInsumoRepository.save(fichaTecnicaInsumo);

        Optional<FichaTecnicaInsumoEntity> encontrado =
                fichaTecnicaInsumoRepository.findById(salvo.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(2.0, encontrado.get().getQuantidade());
        assertEquals("kg", encontrado.get().getUnidade());
    }
}