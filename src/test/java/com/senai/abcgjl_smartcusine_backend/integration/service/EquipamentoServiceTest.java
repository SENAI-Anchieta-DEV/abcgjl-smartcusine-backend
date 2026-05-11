package com.senai.abcgjl_smartcusine_backend.integration.service;

import com.senai.abcgjl_smartcusine_backend.application.mapper.EquipamentoMapper;
import com.senai.abcgjl_smartcusine_backend.application.service.EquipamentoService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.repository.EquipamentoRepository;
import com.senai.abcgjl_smartcusine_backend.application.dto.EquipamentoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.EquipamentoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import com.senai.abcgjl_smartcusine_backend.domain.repository.FichaTecnicaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

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

        service = new EquipamentoService(repository,
                fichaTecnicaRepository
        );

    }

    // ✅ CRIAR
    @Test
    void deveCriarEquipamento() {
        EquipamentoRequestDTO dto = mock(EquipamentoRequestDTO.class);

        FichaTecnicaEntity ficha = new FichaTecnicaEntity();

        EquipamentoEntity entity = new EquipamentoEntity();

        UUID fichaId = UUID.randomUUID();

        when(dto.fichaTecnicaId()).thenReturn(fichaId);

        when(fichaTecnicaRepository.findById(fichaId))
                .thenReturn(Optional.of(ficha));

        when(repository.save(any()))
                .thenReturn(entity);

        EquipamentoResponseDTO result = service.criar(dto);

        assertNotNull(result);
    }

    // ✅ LISTAR
    @Test
    void deveListarEquipamentos() {
        EquipamentoEntity equipamento = new EquipamentoEntity();

        when(repository.findAll()).thenReturn(List.of(equipamento));

        List<EquipamentoResponseDTO> lista = service.listar();

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

        EquipamentoResponseDTO result = service.buscarPorId(id);

        assertNotNull(result);
    }

    // ❌ ATUALIZAR NÃO EXISTE
    @Test
    void deveFalharAoAtualizarQuandoNaoExiste() {
        UUID id = UUID.randomUUID();
        EquipamentoRequestDTO dto = mock(EquipamentoRequestDTO.class);

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.atualizar(id, dto);
        });
    }

    // ✅ ATUALIZAR
    @Test
    void deveAtualizarEquipamento() {
        UUID id = UUID.randomUUID();
        UUID fichaId = UUID.randomUUID();

        EquipamentoRequestDTO dto = mock(EquipamentoRequestDTO.class);
        EquipamentoEntity existente = new EquipamentoEntity();
        FichaTecnicaEntity ficha = new FichaTecnicaEntity();

        when(repository.findById(id)).thenReturn(Optional.of(existente));
        when(fichaTecnicaRepository.findById(fichaId)).thenReturn(Optional.of(ficha));
        when(dto.fichaTecnicaId()).thenReturn(fichaId);
        when(dto.tipo()).thenReturn("Geladeira");
        when(dto.temperaturaAtual()).thenReturn(4.0);
        when(dto.temperaturaIdeal()).thenReturn(2.0);
        when(repository.save(any())).thenReturn(existente);

        EquipamentoResponseDTO result = service.atualizar(id, dto);

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
