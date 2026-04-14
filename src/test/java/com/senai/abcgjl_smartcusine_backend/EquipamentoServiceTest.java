package com.senai.abcgjl_smartcusine_backend;

import com.senai.abcgjl_smartcusine_backend.application.service.EquipamentoService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.exception.EquipamentoNaoEncontradoException;
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
}
