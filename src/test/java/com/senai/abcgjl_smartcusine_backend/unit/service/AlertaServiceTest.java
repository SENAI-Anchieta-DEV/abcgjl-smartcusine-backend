package com.senai.abcgjl_smartcusine_backend.unit.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.AlertaResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.AlertaService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.AlertaEntity;
import com.senai.abcgjl_smartcusine_backend.domain.exception.AlertaNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.AlertaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlertaServiceTest {
    private AlertaRepository repository;
    private AlertaService service;

    @BeforeEach
    void setup() {
        repository = mock(AlertaRepository.class);
        service = new AlertaService(repository);
    }

    // Task 2

    // ✅ LISTAR ALERTAS
    @Test
    void deveListarAlertas() {
        AlertaEntity alerta = new AlertaEntity();

        when(repository.findAll()).thenReturn(List.of(alerta));

        List<AlertaResponseDTO> lista = service.listar();

        assertNotNull(lista);
        assertFalse(lista.isEmpty());

        verify(repository).findAll();
    }

    // ✅ LISTA VAZIA
    @Test
    void deveRetornarListaVaziaQuandoNaoExistemAlertas() {
        when(repository.findAll()).thenReturn(List.of());

        List<AlertaResponseDTO> lista = service.listar();

        assertNotNull(lista);
        assertTrue(lista.isEmpty());

        verify(repository).findAll();
    }

    // Task 1 e 2
    // ❌ DELETAR QUANDO NÃO EXISTE
    @Test
    void deveFalharAoDeletarQuandoNaoExiste() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(AlertaNaoEncontradoException.class, () -> {
            service.deletar(id);
        });
        verify(repository).existsById(id);
    }

    // ✅ DELETAR COM SUCESSO
    @Test
    void deveDeletarAlertaComSucesso() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> service.deletar(id));
        verify(repository).existsById(id);
        verify(repository).deleteById(id);

    }

    @Test
    void naoDeveChamarDeleteQuandoAlertaNaoExiste() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(AlertaNaoEncontradoException.class, () -> {
            service.deletar(id);
        });

        verify(repository, never()).deleteById(id);
    }

    @Test
    void naoDeveDeletarQuandoNaoExiste() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(AlertaNaoEncontradoException.class, () -> {
            service.deletar(id);
        });

        verify(repository, never()).deleteById(id);
    }
}