package com.senai.abcgjl_smartcusine_backend.application.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.EquipamentoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.EquipamentoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.mapper.EquipamentoMapper;
import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import com.senai.abcgjl_smartcusine_backend.domain.exception.EquipamentoNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.exception.FichaTecnicaNaoEncontradaException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.EquipamentoRepository;
import com.senai.abcgjl_smartcusine_backend.domain.repository.FichaTecnicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final FichaTecnicaRepository fichaTecnicaRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository,
                              FichaTecnicaRepository fichaTecnicaRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.fichaTecnicaRepository = fichaTecnicaRepository;
    }

    public EquipamentoResponseDTO criar(EquipamentoRequestDTO dto) {
        FichaTecnicaEntity fichaTecnica = fichaTecnicaRepository.findById(dto.fichaTecnicaId())
                .orElseThrow(() -> new FichaTecnicaNaoEncontradaException("Ficha técnica não encontrada"));

        EquipamentoEntity equipamento = new EquipamentoEntity();
        equipamento.setTipo(dto.tipo());
        equipamento.setTemperaturaAtual(dto.temperaturaAtual());
        equipamento.setTemperaturaIdeal(dto.temperaturaIdeal());
        equipamento.setFichaTecnica(fichaTecnica);

        EquipamentoEntity salvo = equipamentoRepository.save(equipamento);
        return EquipamentoMapper.toDTO(salvo);
    }

    public List<EquipamentoResponseDTO> listar() {
        return equipamentoRepository.findAll()
                .stream()
                .map(EquipamentoMapper::toDTO)
                .toList();
    }

    public EquipamentoResponseDTO buscarPorId(UUID id) {
        EquipamentoEntity equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new EquipamentoNaoEncontradoException("Equipamento não encontrado"));

        return EquipamentoMapper.toDTO(equipamento);
    }

    public EquipamentoResponseDTO atualizar(UUID id, EquipamentoRequestDTO dto) {
        EquipamentoEntity equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new EquipamentoNaoEncontradoException("Equipamento não encontrado"));

        FichaTecnicaEntity fichaTecnica = fichaTecnicaRepository.findById(dto.fichaTecnicaId())
                .orElseThrow(() -> new FichaTecnicaNaoEncontradaException("Ficha técnica não encontrada"));

        equipamento.setTipo(dto.tipo());
        equipamento.setTemperaturaAtual(dto.temperaturaAtual());
        equipamento.setTemperaturaIdeal(dto.temperaturaIdeal());
        equipamento.setFichaTecnica(fichaTecnica);

        EquipamentoEntity atualizado = equipamentoRepository.save(equipamento);
        return EquipamentoMapper.toDTO(atualizado);
    }

    public void deletar(UUID id) {
        EquipamentoEntity equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new EquipamentoNaoEncontradoException("Equipamento não encontrado"));

        equipamentoRepository.delete(equipamento);
    }
}