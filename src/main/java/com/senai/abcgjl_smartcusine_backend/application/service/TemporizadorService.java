package com.senai.abcgjl_smartcusine_backend.application.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.TemporizadorDTO;
import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.entity.TemporizadorEntity;
import com.senai.abcgjl_smartcusine_backend.domain.exception.EquipamentoNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.exception.TemporizadorNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.EquipamentoRepository;
import com.senai.abcgjl_smartcusine_backend.domain.repository.TemporizadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TemporizadorService {
    private final TemporizadorRepository temporizadorRepository;
    private final EquipamentoRepository equipamentoRepository;

    public TemporizadorService(TemporizadorRepository temporizadorRepository, EquipamentoRepository equipamentoRepository) {
        this.temporizadorRepository = temporizadorRepository;
        this.equipamentoRepository = equipamentoRepository;
    }

    public TemporizadorDTO criar(TemporizadorDTO dto) {
        TemporizadorEntity temporizador = new TemporizadorEntity();
        temporizador.setTempoConfigurado(dto.tempoConfigurado());
        temporizador.setTempoAtual(dto.tempoAtual());

        EquipamentoEntity equipamento = equipamentoRepository.findById(dto.equipamentoId())
                .orElseThrow(() -> new EquipamentoNaoEncontradoException("Equipamento não encontrado"));
        temporizador.setEquipamento(equipamento);

        TemporizadorEntity salvo = temporizadorRepository.save(temporizador);
        return mapToDTO(salvo);
    }

    public List<TemporizadorDTO> listarTodos() {
        return temporizadorRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public TemporizadorDTO buscarPorId(UUID id) {
        TemporizadorEntity temporizador = temporizadorRepository.findById(id)
                .orElseThrow(() -> new TemporizadorNaoEncontradoException());
        return mapToDTO(temporizador);
    }

    public TemporizadorDTO atualizar(UUID id, TemporizadorDTO dto) {
        TemporizadorEntity temporizador = temporizadorRepository.findById(id)
                .orElseThrow(() -> new TemporizadorNaoEncontradoException());

        temporizador.setTempoConfigurado(dto.tempoConfigurado());
        temporizador.setTempoAtual(dto.tempoAtual());

        if (dto.equipamentoId() != null) {
            EquipamentoEntity equipamento = equipamentoRepository.findById(dto.equipamentoId())
                    .orElseThrow(() -> new EquipamentoNaoEncontradoException("Equipamento não encontrado"));
            temporizador.setEquipamento(equipamento);
        }

        TemporizadorEntity salvo = temporizadorRepository.save(temporizador);
        return mapToDTO(salvo);
    }

    public void deletar(UUID id) {
        TemporizadorEntity temporizador = temporizadorRepository.findById(id)
                .orElseThrow(() -> new TemporizadorNaoEncontradoException());
        temporizadorRepository.delete(temporizador);
    }

    private TemporizadorDTO mapToDTO(TemporizadorEntity entity) {
        return new TemporizadorDTO(
                entity.getIdTemporizador(),
                entity.getTempoConfigurado(),
                entity.getTempoAtual(),
                entity.getEquipamento() != null ? entity.getEquipamento().getIdEquipamento() : null
        );
    }
}
