package com.senai.abcgjl_smartcusine_backend.unit.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaInsumoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaInsumoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.FichaTecnicaInsumoService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaInsumoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.entity.InsumoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.repository.FichaTecnicaInsumoRepository;
import com.senai.abcgjl_smartcusine_backend.domain.repository.FichaTecnicaRepository;
import com.senai.abcgjl_smartcusine_backend.domain.repository.InsumoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class FichaTecnicaInsumoServiceTest {

    private FichaTecnicaInsumoRepository relacaoRepository;
    private FichaTecnicaRepository fichaRepository;
    private InsumoRepository insumoRepository;
    private FichaTecnicaInsumoService service;

    @Mock
    private InsumoRepository InsumoRepository;


    @InjectMocks
    private FichaTecnicaInsumoService fichaTecnicaInsumoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setup() {
        relacaoRepository = mock(FichaTecnicaInsumoRepository.class);
        fichaRepository = mock(FichaTecnicaRepository.class);
        insumoRepository = mock(InsumoRepository.class);

        service = new FichaTecnicaInsumoService(
                relacaoRepository,
                fichaRepository,
                insumoRepository
        );
    }

    // ❌ QUANTIDADE INVÁLIDA
    @Test
    void deveFalharQuandoQuantidadeInvalida() {
        UUID fichaId = UUID.randomUUID();
        UUID insumoId = UUID.randomUUID();

        when(relacaoRepository.existsByFichaTecnicaIdAndInsumoId(fichaId, insumoId))
                .thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            service.adicionarInsumo(fichaId, insumoId, 0.0);
        });
    }

// Adicionar: Quantidade null

    @Test
    void deveFalharQuandoQuantidadeForNula() {

        UUID fichaId = UUID.randomUUID();
        UUID insumoId = UUID.randomUUID();

        when(relacaoRepository.existsByFichaTecnicaIdAndInsumoId(fichaId, insumoId))
                .thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            service.adicionarInsumo(fichaId, insumoId, null);
        });
    }
    // RELAÇÃO JÁ EXISTE
    @Test
    void deveFalharQuandoInsumoJaExisteNaFicha() {

        UUID fichaId = UUID.randomUUID();
        UUID insumoId = UUID.randomUUID();

        when(relacaoRepository.existsByFichaTecnicaIdAndInsumoId(fichaId, insumoId))
                .thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            service.adicionarInsumo(fichaId, insumoId, 10.0);
        });
        verify(relacaoRepository).existsByFichaTecnicaIdAndInsumoId(fichaId, insumoId);
    }

    // ❌ FICHA NÃO EXISTE
    @Test
    void deveFalharQuandoFichaNaoExiste() {

        UUID fichaId = UUID.randomUUID();
        UUID insumoId = UUID.randomUUID();

        when(relacaoRepository.existsByFichaTecnicaIdAndInsumoId(fichaId, insumoId))
                .thenReturn(false);

        when(fichaRepository.findById(fichaId))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.adicionarInsumo(fichaId, insumoId, 10.0);
        });
        verify(fichaRepository).findById(fichaId);
    }

    // ❌ INSUMO NÃO EXISTE
    @Test
    void deveFalharQuandoInsumoNaoExiste() {

        UUID fichaId = UUID.randomUUID();
        UUID insumoId = UUID.randomUUID();

        when(relacaoRepository.existsByFichaTecnicaIdAndInsumoId(fichaId, insumoId))
                .thenReturn(false);

        FichaTecnicaEntity ficha = new FichaTecnicaEntity();

        when(fichaRepository.findById(fichaId))
                .thenReturn(Optional.of(ficha));

        when(insumoRepository.findById(insumoId))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.adicionarInsumo(fichaId, insumoId, 10.0);
        });

        verify(insumoRepository).findById(insumoId);
    }

    // ✅ ADICIONAR COM SUCESSO
    @Test
    void deveAdicionarInsumoComSucesso() {

        UUID fichaId = UUID.randomUUID();
        UUID insumoId = UUID.randomUUID();

        FichaTecnicaEntity ficha = new FichaTecnicaEntity();
        InsumoEntity insumo = new InsumoEntity();

        when(relacaoRepository.existsByFichaTecnicaIdAndInsumoId(fichaId, insumoId))
                .thenReturn(false);

        when(fichaRepository.findById(fichaId))
                .thenReturn(Optional.of(ficha));

        when(insumoRepository.findById(insumoId))
                .thenReturn(Optional.of(insumo));

        when(relacaoRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        FichaTecnicaInsumoResponseDTO result =
                service.adicionarInsumo(fichaId, insumoId, 5.0);

        assertNotNull(result);

        verify(relacaoRepository).save(any());

        verify(relacaoRepository).existsByFichaTecnicaIdAndInsumoId(fichaId, insumoId);
        verify(fichaRepository).findById(fichaId);
        verify(insumoRepository).findById(insumoId);
    }

    // ✅ LISTAR
    @Test
    void deveListarRelacoes() {


        InsumoEntity insumo = new InsumoEntity();
        insumo.setNome("Arroz");

        FichaTecnicaEntity ficha = new FichaTecnicaEntity();
        ficha.setNomePreparo("Arroz branco");

        FichaTecnicaInsumoEntity entity = new FichaTecnicaInsumoEntity();
        entity.setInsumo(insumo);
        entity.setFichaTecnica(ficha);
        entity.setQuantidade(2.0);

        when(relacaoRepository.findAll())
                .thenReturn(List.of(entity));

        List<FichaTecnicaInsumoResponseDTO> lista = service.listar();

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());

        verify(relacaoRepository).findAll();
    }

    // ❌ ATUALIZAR QUANTIDADE INVÁLIDA
    @Test
    void deveFalharAoAtualizarQuantidadeInvalida() {
        UUID id = UUID.randomUUID();

        FichaTecnicaInsumoRequestDTO dto = mock(FichaTecnicaInsumoRequestDTO.class);

        when(dto.quantidade()).thenReturn(0.0);

        assertThrows(IllegalArgumentException.class, () -> {
            service.atualizar(id, dto);
        });
    }

    // ❌ ATUALIZAR NÃO EXISTE
    @Test
    void deveFalharQuandoRelacaoNaoExiste() {
        UUID id = UUID.randomUUID();

        FichaTecnicaInsumoRequestDTO dto = mock(FichaTecnicaInsumoRequestDTO.class);

        when(dto.quantidade()).thenReturn(10.0);
        when(relacaoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.atualizar(id, dto);
        });

        verify(relacaoRepository).findById(id);
    }

    // ✅ ATUALIZAR COM SUCESSO
    @Test
    void deveAtualizarRelacao() {
        UUID id = UUID.randomUUID();

        FichaTecnicaInsumoRequestDTO dto = mock(FichaTecnicaInsumoRequestDTO.class);

        when(dto.quantidade()).thenReturn(10.0);

        InsumoEntity insumo = new InsumoEntity();
        insumo.setNome("Arroz");

        FichaTecnicaEntity ficha = new FichaTecnicaEntity();
        ficha.setNomePreparo("Arroz branco");

        FichaTecnicaInsumoEntity entity = new FichaTecnicaInsumoEntity();
        entity.setInsumo(insumo);
        entity.setFichaTecnica(ficha);
        entity.setQuantidade(5.0);

        when(relacaoRepository.findById(id))
                .thenReturn(Optional.of(entity));

        when(relacaoRepository.save(any()))
                .thenReturn(entity);

        FichaTecnicaInsumoResponseDTO result =
                service.atualizar(id, dto);

        assertNotNull(result);
        verify(relacaoRepository).save(entity);
    }

    // ❌ DELETAR
    @Test
    void deveDeletarRelacao() {
        UUID id = UUID.randomUUID();

        when(relacaoRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> service.deletar(id));

        verify(relacaoRepository).deleteById(id);
    }

    @Test
    void deveFalharQuandoFichaTecnicaNaoPossuiDados() {
        UUID fichaId = UUID.randomUUID();
        UUID insumoId = UUID.randomUUID();

        FichaTecnicaEntity ficha = new FichaTecnicaEntity();
        ficha.setNomePreparo("");  // Ficha vazia

        when(fichaRepository.findById(fichaId))
                .thenReturn(Optional.of(ficha));

        assertThrows(RuntimeException.class, () -> {
            service.adicionarInsumo(fichaId, insumoId, 10.0);
        });

        verify(fichaRepository).findById(fichaId);
    }

    @Test
    void deveFalharQuandoInsumoNaoPossuiNome() {
        UUID fichaId = UUID.randomUUID();
        UUID insumoId = UUID.randomUUID();

        InsumoEntity insumo = new InsumoEntity();
        insumo.setNome(null);

        FichaTecnicaEntity ficha = new FichaTecnicaEntity();

        when(relacaoRepository.existsByFichaTecnicaIdAndInsumoId(fichaId, insumoId))
                .thenReturn(false);

        when(fichaRepository.findById(fichaId))
                .thenReturn(Optional.of(ficha));

        when(insumoRepository.findById(insumoId))
                .thenReturn(Optional.of(insumo));

        assertThrows(RuntimeException.class, () -> {
            service.adicionarInsumo(fichaId, insumoId, 10.0);
        });

        verify(insumoRepository).findById(insumoId);
    }

    @Test
    void deveFalharAoDeletarQuandoIdForNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.deletar(null);
        });

        verify(relacaoRepository, never()).deleteById(any());
    }
    @Test
    void deveFalharAoDeletarQuandoNaoExiste() {
        UUID id = UUID.randomUUID();

        when(relacaoRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            service.deletar(id);
        });

        verify(relacaoRepository, never()).deleteById(any());
    }
    @Test
    void deveFalharQuandoDtoForNulo() {
        UUID id = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            service.atualizar(id, null);
        });
    }
    @Test
    void deveFalharQuandoIdsForemNulos() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.adicionarInsumo(null, null, 10.0);
        });
    }
}

