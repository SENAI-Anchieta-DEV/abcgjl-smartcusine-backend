package com.senai.abcgjl_smartcusine_backend.unit.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.TemporizadorDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.TemporizadorService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.entity.TemporizadorEntity;
import com.senai.abcgjl_smartcusine_backend.domain.exception.EquipamentoNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.exception.TemporizadorNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.EquipamentoRepository;
import com.senai.abcgjl_smartcusine_backend.domain.repository.TemporizadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

public class TemporizadorServiceTest {
    private TemporizadorRepository temporizadorRepository;
    private EquipamentoRepository equipamentoRepository;
    private TemporizadorService service;

    @BeforeEach
    void setup() {
        temporizadorRepository = mock(TemporizadorRepository.class);
        equipamentoRepository = mock(EquipamentoRepository.class);
        service = new TemporizadorService(temporizadorRepository, equipamentoRepository);
    }

    // ✅ SUCESSO
    @Test
    void deveCriarTemporizador() {
        UUID equipamentoId = UUID.randomUUID();

        TemporizadorDTO dto = new TemporizadorDTO(null, 100, 50, equipamentoId);

        EquipamentoEntity equipamento = new EquipamentoEntity();
        equipamento.setIdEquipamento(equipamentoId);

        when(equipamentoRepository.findById(equipamentoId))
                .thenReturn(Optional.of(equipamento));

        when(temporizadorRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        TemporizadorDTO resultado = service.criar(dto);

        assertNotNull(resultado);

        verify(equipamentoRepository).findById(equipamentoId);
        verify(temporizadorRepository).save(any());

        verifyNoMoreInteractions(equipamentoRepository, temporizadorRepository);
    }

    // ❌ EQUIPAMENTO NÃO EXISTE
    @Test
    void deveLancarErroQuandoEquipamentoNaoExiste() {
        UUID equipamentoId = UUID.randomUUID();

        TemporizadorDTO dto = new TemporizadorDTO(null, 100, 50, equipamentoId);

        when(equipamentoRepository.findById(equipamentoId))
                .thenReturn(Optional.empty());

        assertThrows(EquipamentoNaoEncontradoException.class, () -> {
            service.criar(dto);
        });

        verify(equipamentoRepository).findById(equipamentoId);
        verifyNoInteractions(temporizadorRepository);
    }

    // ✅ LISTAR
    @Test
    void deveListarTemporizadores() {

        TemporizadorEntity t = new TemporizadorEntity();
        t.setIdTemporizador(UUID.randomUUID());
        when(temporizadorRepository.findAll())
                .thenReturn(List.of(t));

        List<TemporizadorDTO> lista = service.listarTodos();

        assertNotNull(lista);
        assertEquals(1, lista.size());

        verify(temporizadorRepository).findAll();
    }

    // ✅ BUSCAR POR ID
    @Test
    void deveBuscarPorId() {
        UUID id = UUID.randomUUID();

        TemporizadorEntity t = new TemporizadorEntity();
        t.setIdTemporizador(id);

        when(temporizadorRepository.findById(id))
                .thenReturn(Optional.of(t));

        TemporizadorDTO resultado = service.buscarPorId(id);

        assertNotNull(resultado);

        verify(temporizadorRepository).findById(id);
    }

    // ❌ NÃO ENCONTRADO
    @Test
    void deveLancarErroQuandoNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(temporizadorRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(TemporizadorNaoEncontradoException.class, () -> {
            service.buscarPorId(id);
        });

        verify(temporizadorRepository).findById(id);
        verifyNoInteractions(equipamentoRepository);
    }

    // ✅ ATUALIZAR
    @Test
    void deveAtualizarTemporizador() {
        UUID id = UUID.randomUUID();
        UUID equipamentoId = UUID.randomUUID();

        TemporizadorEntity temporizador = new TemporizadorEntity();
        temporizador.setIdTemporizador(id);

        EquipamentoEntity equipamento = new EquipamentoEntity();
        equipamento.setIdEquipamento(equipamentoId);

        TemporizadorDTO dto = new TemporizadorDTO(null, 200, 100, equipamentoId);

        when(temporizadorRepository.findById(id))
                .thenReturn(Optional.of(temporizador));

        when(equipamentoRepository.findById(equipamentoId))
                .thenReturn(Optional.of(equipamento));

        when(temporizadorRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        TemporizadorDTO resultado = service.atualizar(id, dto); // ALTERAR ✔

        assertNotNull(resultado);

        verify(temporizadorRepository).findById(id);
        verify(equipamentoRepository).findById(equipamentoId);
        verify(temporizadorRepository).save(any());
    }

    // ❌ ATUALIZAR COM EQUIPAMENTO INVÁLIDO
    @Test
    void deveFalharAoAtualizarComEquipamentoInvalido() {
        UUID id = UUID.randomUUID();
        UUID equipamentoId = UUID.randomUUID();

        TemporizadorEntity temporizador = new TemporizadorEntity();
        temporizador.setIdTemporizador(id);

        TemporizadorDTO dto = new TemporizadorDTO(null, 200, 100, equipamentoId);

        when(temporizadorRepository.findById(id))
                .thenReturn(Optional.of(temporizador));

        when(equipamentoRepository.findById(equipamentoId))
                .thenReturn(Optional.empty());

        assertThrows(EquipamentoNaoEncontradoException.class, () -> {
            service.atualizar(id, dto);
        });

        verify(temporizadorRepository).findById(id);
        verify(equipamentoRepository).findById(equipamentoId);
        verify(temporizadorRepository, never()).save(any());
    }

    // ❌ DELETAR NÃO ENCONTRADO
    @Test
    void deveFalharAoDeletarQuandoNaoExiste() {
        UUID id = UUID.randomUUID();

        when(temporizadorRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(TemporizadorNaoEncontradoException.class, () -> {
            service.deletar(id);
        });
        verify(temporizadorRepository).findById(id);
        verify(temporizadorRepository, never()).delete(any());
    }

    // ✅ DELETAR
    @Test
    void deveDeletarTemporizador() {
        UUID id = UUID.randomUUID();

        TemporizadorEntity temporizador = new TemporizadorEntity();
        temporizador.setIdTemporizador(id);

        when(temporizadorRepository.findById(id))
                .thenReturn(Optional.of(temporizador));

        assertDoesNotThrow(() -> service.deletar(id));

        verify(temporizadorRepository).findById(id);
        verify(temporizadorRepository).delete(temporizador);
    }
}
