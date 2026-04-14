package com.senai.abcgjl_smartcusine_backend;


import com.senai.abcgjl_smartcusine_backend.application.dto.InsumoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.InsumoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.mapper.InsumoMapper;
import com.senai.abcgjl_smartcusine_backend.application.service.InsumoService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.InsumoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.exception.InsumoNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.InsumoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InsumoServiceTest {

    private InsumoRepository repository;
    private InsumoMapper mapper;
    private InsumoService service;

    @BeforeEach
    void setup() {
        repository = mock(InsumoRepository.class);
        mapper = mock(InsumoMapper.class);
        service = new InsumoService(repository, mapper);
    }

    // ✅ SUCESSO
    @Test
    void deveCriarInsumo() {
        InsumoRequestDTO dto = mock(InsumoRequestDTO.class);
        InsumoEntity entity = new InsumoEntity();
        InsumoResponseDTO response = mock(InsumoResponseDTO.class);

        when(dto.nome()).thenReturn("Arroz");
        when(repository.existsByNome("Arroz")).thenReturn(false);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        InsumoResponseDTO resultado = service.criar(dto);

        assertNotNull(resultado);

        assertNotNull(entity.getQrCode());

        verify(repository).save(entity);

        verify(mapper).toEntity(dto);
        verify(mapper).toResponse(entity);

        assertTrue(entity.getQrCode().startsWith("INSUMO-"));

    }

    // ❌ NOME DUPLICADO
    @Test
    void deveLancarErroQuandoNomeDuplicado() {

        InsumoRequestDTO dto = mock(InsumoRequestDTO.class);

        when(dto.nome()).thenReturn("Arroz");
        when(repository.existsByNome("Arroz")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            service.criar(dto);
        });
    }

    // ✅ LISTAR
    @Test
    void deveListarInsumos() {
        InsumoEntity entity = new InsumoEntity();
        InsumoResponseDTO response = mock(InsumoResponseDTO.class);

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        List<InsumoResponseDTO> lista = service.listar();
        assertNotNull(lista);
        assertEquals(1, lista.size());

        verify(mapper).toResponse(entity);
    }

    // ✅ BUSCAR POR ID
    @Test
    void deveBuscarPorId() {
        UUID id = UUID.randomUUID();

        InsumoEntity entity = new InsumoEntity();
        InsumoResponseDTO response = mock(InsumoResponseDTO.class);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);
        InsumoResponseDTO resultado = service.buscarPorId(id);

        assertNotNull(resultado);

        verify(mapper).toResponse(entity);
    }

    // ❌ NÃO ENCONTRADO
    @Test
    void deveLancarErroQuandoNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(InsumoNaoEncontradoException.class, () -> {
            service.buscarPorId(id);
        });
    }

    // ✅ ATUALIZAR
    @Test
    void deveAtualizarInsumo() {
        UUID id = UUID.randomUUID();

        InsumoRequestDTO dto = mock(InsumoRequestDTO.class);
        InsumoEntity entity = new InsumoEntity();
        InsumoResponseDTO response = mock(InsumoResponseDTO.class);

        entity.setNome("Antigo");

        when(dto.nome()).thenReturn("Novo");
        when(dto.unidadeMedida()).thenReturn("kg");
        when(dto.quantidadeEstoque()).thenReturn(10.0);
        when(dto.dataValidade()).thenReturn(null);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.existsByNome("Novo")).thenReturn(false);
        when(mapper.toResponse(entity)).thenReturn(response);

        InsumoResponseDTO resultado = service.atualizar(id, dto);

        assertNotNull(resultado);
        verify(repository).save(entity);
        verify(mapper).toResponse(entity);
    }

    // ❌ ATUALIZAR COM NOME DUPLICADO
    @Test
    void deveFalharAoAtualizarComNomeDuplicado() {
        UUID id = UUID.randomUUID();

        InsumoRequestDTO dto = mock(InsumoRequestDTO.class);
        InsumoEntity entity = new InsumoEntity();

        entity.setNome("Original");

        when(dto.nome()).thenReturn("Duplicado");

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.existsByNome("Duplicado")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            service.atualizar(id, dto);
        });
    }
    // Id não existe na atualização
    @Test
    void deveLancarErroQuandoIdNaoExisteNaAtualizacao() {

        UUID id = UUID.randomUUID();

        InsumoRequestDTO dto = mock(InsumoRequestDTO.class);

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(InsumoNaoEncontradoException.class, () -> {
            service.atualizar(id, dto);
        });
    }

    // ❌ DELETAR NÃO ENCONTRADO
    @Test
    void deveFalharAoDeletarQuandoNaoExiste() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(InsumoNaoEncontradoException.class, () -> {
            service.deletar(id);
        });
    }

    // ✅ DELETAR
    @Test
    void deveDeletarInsumo() {
        UUID id = UUID.randomUUID();

        InsumoEntity entity = new InsumoEntity();

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        assertDoesNotThrow(() -> service.deletar(id));
        verify(repository).delete(entity);
    }

}
