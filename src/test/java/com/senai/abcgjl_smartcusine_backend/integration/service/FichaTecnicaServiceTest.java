package com.senai.abcgjl_smartcusine_backend.integration.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.FichaTecnicaService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import com.senai.abcgjl_smartcusine_backend.domain.exception.FichaTecnicaNaoEncontradaException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.FichaTecnicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class FichaTecnicaServiceTest {
    private FichaTecnicaRepository repository;
    private FichaTecnicaService service;

    @BeforeEach
    void setup() {
        repository = mock(FichaTecnicaRepository.class);
        service = new FichaTecnicaService(repository);
    }

    // ✅ CRIAR COM SUCESSO
    @Test
    void deveCriarFichaTecnica() {
        FichaTecnicaRequestDTO dto = mock(FichaTecnicaRequestDTO.class);
        FichaTecnicaEntity entity = new FichaTecnicaEntity();
        FichaTecnicaEntity saved = new FichaTecnicaEntity();

        when(dto.getNomePreparo()).thenReturn("Bolo");
        when(repository.existsByNomePreparo("Bolo")).thenReturn(false);
        when(repository.save(any())).thenReturn(saved);

        FichaTecnicaResponseDTO result = service.criar(dto);

        assertNotNull(result);
        verify(repository).save(any());
    }

    // ❌ NOME DUPLICADO (CRIAÇÃO)
    @Test
    void deveLancarErroQuandoNomeDuplicadoNaCriacao() {
        FichaTecnicaRequestDTO dto = mock(FichaTecnicaRequestDTO.class);

        when(dto.getNomePreparo()).thenReturn("Bolo");
        when(repository.existsByNomePreparo("Bolo")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            service.criar(dto);
        });
    }

    // ✅ LISTAR
    @Test
    void deveListarFichas() {
        FichaTecnicaEntity entity = new FichaTecnicaEntity();

        when(repository.findAll()).thenReturn(List.of(entity));

        List<FichaTecnicaResponseDTO> lista = service.listar();

        assertFalse(lista.isEmpty());
    }

    // ❌ BUSCAR PARA ATUALIZAÇÃO - NÃO EXISTE
    @Test
    void deveFalharQuandoFichaNaoExisteNaAtualizacao() {
        UUID id = UUID.randomUUID();

        FichaTecnicaRequestDTO dto = mock(FichaTecnicaRequestDTO.class);

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(FichaTecnicaNaoEncontradaException.class, () -> {
            service.atualizar(id, dto);
        });
    }

    // ❌ NOME DUPLICADO NA ATUALIZAÇÃO
    @Test
    void deveLancarErroQuandoNomeDuplicadoNaAtualizacao() {
        UUID id = UUID.randomUUID();

        FichaTecnicaRequestDTO dto = mock(FichaTecnicaRequestDTO.class);
        FichaTecnicaEntity ficha = new FichaTecnicaEntity();
        ficha.setNomePreparo("Antigo");

        when(dto.getNomePreparo()).thenReturn("Novo");

        when(repository.findById(id)).thenReturn(Optional.of(ficha));
        when(repository.existsByNomePreparo("Novo")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            service.atualizar(id, dto);
        });
    }

    // ✅ ATUALIZAR COM SUCESSO
    @Test
    void deveAtualizarFichaTecnica() {
        UUID id = UUID.randomUUID();

        FichaTecnicaRequestDTO dto = mock(FichaTecnicaRequestDTO.class);
        FichaTecnicaEntity ficha = new FichaTecnicaEntity();

        ficha.setNomePreparo("Antigo");

        when(dto.getNomePreparo()).thenReturn("Novo");
        when(dto.getTempoIdeal()).thenReturn(String.valueOf(30));
        when(dto.getTemperaturaIdeal()).thenReturn(180.0);

        when(repository.findById(id)).thenReturn(Optional.of(ficha));
        when(repository.existsByNomePreparo("Novo")).thenReturn(false);
        when(repository.save(any())).thenReturn(ficha);

        FichaTecnicaResponseDTO result = service.atualizar(id, dto);

        assertNotNull(result);
        verify(repository).save(ficha);
    }

    // ❌ DELETAR NÃO EXISTE
    @Test
    void deveLancarErroAoDeletarInexistente() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(FichaTecnicaNaoEncontradaException.class, () -> {
            service.deletar(id);
        });
    }

    // ✅ DELETAR COM SUCESSO
    @Test
    void deveDeletarFichaTecnica() {
        UUID id = UUID.randomUUID();

        FichaTecnicaEntity ficha = new FichaTecnicaEntity();

        when(repository.findById(id)).thenReturn(Optional.of(ficha));

        assertDoesNotThrow(() -> service.deletar(id));
        verify(repository).delete(ficha);
    }
}
