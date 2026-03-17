package com.senai.abcgjl_smartcusine_backend.application.service;

import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EquipamentoService {
    private final EquipamentoRepository repository;

    public EquipamentoService(EquipamentoRepository repository) {
        this.repository = repository;
    }

    public EquipamentoEntity criar(EquipamentoEntity equipamento) {
        return repository.save(equipamento);
    }

    public List<EquipamentoEntity> listar() {
        return repository.findAll();
    }

    public EquipamentoEntity buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
    }

    public EquipamentoEntity atualizar(UUID id, EquipamentoEntity equipamento) {

        EquipamentoEntity existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        existente.setTipo(equipamento.getTipo());
        existente.setTemperaturaAtual(equipamento.getTemperaturaAtual());
        existente.setTemperaturaIdeal(equipamento.getTemperaturaIdeal());
        existente.setFichaTecnica(equipamento.getFichaTecnica());

        return repository.save(existente);
    }

    public void deletar(UUID id) {

        EquipamentoEntity equipamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        repository.delete(equipamento);
    }
}

