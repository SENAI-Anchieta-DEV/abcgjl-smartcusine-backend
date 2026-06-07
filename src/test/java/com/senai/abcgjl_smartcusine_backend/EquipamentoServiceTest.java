/*package com.senai.abcgjl_smartcusine_backend;

import com.senai.abcgjl_smartcusine_backend.application.service.EquipamentoService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.repository.EquipamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EquipamentoServiceTest {
    private EquipamentoRepository repository;
    private EquipamentoService service;

    @BeforeEach
    void setup() {
        repository = mock(EquipamentoRepository.class);
        service = new EquipamentoService(repository);
    }

    // ✅ CRIAR
    @Test
    void deveCriarEquipamento() {
        EquipamentoEntity equipamento = new EquipamentoEntity();

        when(repository.save(equipamento)).thenReturn(equipamento);

        EquipamentoEntity result = service.criar(equipamento);

        assertNotNull(result);
        verify(repository).save(equipamento);
    }

    // ✅ LISTAR
    @Test
    void deveListarEquipamentos() {
        EquipamentoEntity equipamento = new EquipamentoEntity();

        when(repository.findAll()).thenReturn(List.of(equipamento));

        List<EquipamentoEntity> lista = service.listar();

        assertFalse(lista.isEmpty());
    }

    // ❌ BUSCAR POR ID NÃO EXISTE
    @Test
    void deveFalharQuandoEquipamentoNaoExiste() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(id);
        });

        assertEquals("Equipamento não encontrado", ex.getMessage());
    }

    // ✅ BUSCAR POR ID
    @Test
    void deveBuscarEquipamentoPorId() {
        UUID id = UUID.randomUUID();

        EquipamentoEntity equipamento = new EquipamentoEntity();

        when(repository.findById(id)).thenReturn(Optional.of(equipamento));

        EquipamentoEntity result = service.buscarPorId(id);

        assertNotNull(result);
    }

    // ❌ ATUALIZAR NÃO EXISTE
    @Test
    void deveFalharAoAtualizarQuandoNaoExiste() {
        UUID id = UUID.randomUUID();
        EquipamentoEntity novo = new EquipamentoEntity();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.atualizar(id, novo);
        });
    }

    // ✅ ATUALIZAR
    @Test
    void deveAtualizarEquipamento() {
        UUID id = UUID.randomUUID();

        EquipamentoEntity existente = new EquipamentoEntity();
        EquipamentoEntity novo = new EquipamentoEntity();

        when(repository.findById(id)).thenReturn(Optional.of(existente));
        when(repository.save(any())).thenReturn(existente);

        EquipamentoEntity result = service.atualizar(id, novo);

        assertNotNull(result);
        verify(repository).save(existente);
    }

    // ❌ DELETAR NÃO EXISTE
    @Test
    void deveFalharAoDeletarQuandoNaoExiste() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.deletar(id);
        });
    }

    // ✅ DELETAR
    @Test
    void deveDeletarEquipamento() {
        UUID id = UUID.randomUUID();

        EquipamentoEntity equipamento = new EquipamentoEntity();

        when(repository.findById(id)).thenReturn(Optional.of(equipamento));

        assertDoesNotThrow(() -> service.deletar(id));
        verify(repository).delete(equipamento);
    }
}*/
package com.senai.abcgjl_smartcusine_backend;

import com.senai.abcgjl_smartcusine_backend.application.dto.EquipamentoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.EquipamentoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.EquipamentoService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import com.senai.abcgjl_smartcusine_backend.domain.exception.EquipamentoNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.EquipamentoRepository;
import com.senai.abcgjl_smartcusine_backend.domain.repository.FichaTecnicaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EquipamentoServiceTest {

    private EquipamentoRepository repository;
    private FichaTecnicaRepository fichaTecnicaRepository;
    private EquipamentoService service;

    @BeforeEach
    void setup() {
        repository = mock(EquipamentoRepository.class);
        fichaTecnicaRepository = mock(FichaTecnicaRepository.class);

        service = new EquipamentoService(
                repository,
                fichaTecnicaRepository
        );
    }

    @Test
    void deveCriarEquipamento() {

        UUID fichaId = UUID.randomUUID();

        EquipamentoRequestDTO dto =
                new EquipamentoRequestDTO(
                        "Forno",
                        180.0,
                        200.0,
                        fichaId
                );

        FichaTecnicaEntity ficha = new FichaTecnicaEntity();

        EquipamentoEntity equipamento = new EquipamentoEntity();
        equipamento.setFichaTecnica(ficha);

        when(fichaTecnicaRepository.findById(fichaId))
                .thenReturn(Optional.of(ficha));

        when(repository.save(any()))
                .thenReturn(equipamento);

        EquipamentoResponseDTO result =
                service.criar(dto);

        assertNotNull(result);
    }

    @Test
    void deveListarEquipamentos() {

        EquipamentoEntity equipamento =
                new EquipamentoEntity();

        when(repository.findAll())
                .thenReturn(List.of(equipamento));

        List<EquipamentoResponseDTO> lista =
                service.listar();

        assertFalse(lista.isEmpty());
    }

    @Test
    void deveFalharQuandoEquipamentoNaoExiste() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                EquipamentoNaoEncontradoException.class,
                () -> service.buscarPorId(id)
        );
    }

    @Test
    void deveBuscarEquipamentoPorId() {

        UUID id = UUID.randomUUID();

        EquipamentoEntity equipamento =
                new EquipamentoEntity();

        when(repository.findById(id))
                .thenReturn(Optional.of(equipamento));

        EquipamentoResponseDTO result =
                service.buscarPorId(id);

        assertNotNull(result);
    }

    @Test
    void deveFalharAoAtualizarQuandoNaoExiste() {

        UUID id = UUID.randomUUID();
        UUID fichaId = UUID.randomUUID();

        EquipamentoRequestDTO dto =
                new EquipamentoRequestDTO(
                        "Forno",
                        180.0,
                        200.0,
                        fichaId
                );

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                EquipamentoNaoEncontradoException.class,
                () -> service.atualizar(id, dto)
        );
    }

    @Test
    void deveAtualizarEquipamento() {

        UUID id = UUID.randomUUID();
        UUID fichaId = UUID.randomUUID();

        EquipamentoEntity existente =
                new EquipamentoEntity();

        FichaTecnicaEntity ficha =
                new FichaTecnicaEntity();

        EquipamentoRequestDTO dto =
                new EquipamentoRequestDTO(
                        "Forno",
                        180.0,
                        200.0,
                        fichaId
                );

        when(repository.findById(id))
                .thenReturn(Optional.of(existente));

        when(fichaTecnicaRepository.findById(fichaId))
                .thenReturn(Optional.of(ficha));

        when(repository.save(any()))
                .thenReturn(existente);

        EquipamentoResponseDTO result =
                service.atualizar(id, dto);

        assertNotNull(result);
    }

    @Test
    void deveFalharAoDeletarQuandoNaoExiste() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                EquipamentoNaoEncontradoException.class,
                () -> service.deletar(id)
        );
    }

    @Test
    void deveDeletarEquipamento() {

        UUID id = UUID.randomUUID();

        EquipamentoEntity equipamento =
                new EquipamentoEntity();

        when(repository.findById(id))
                .thenReturn(Optional.of(equipamento));

        assertDoesNotThrow(
                () -> service.deletar(id)
        );

        verify(repository)
                .delete(equipamento);
    }
}
