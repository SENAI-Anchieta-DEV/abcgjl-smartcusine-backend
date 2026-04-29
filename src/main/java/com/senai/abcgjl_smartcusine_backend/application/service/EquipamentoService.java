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
        if (equipamento == null) {
            throw new IllegalArgumentException("Equipamento não pode ser nulo");
        }

        if (equipamento.getTipo() == null || equipamento.getTipo().isBlank()) {
            throw new IllegalArgumentException("Tipo não pode ser vazio");
        }

        if (equipamento.getTemperaturaIdeal() < 0) {
            throw new IllegalArgumentException("Temperatura ideal inválida");
        }

        return repository.save(equipamento);
    }

    public List<EquipamentoEntity> listar() {
        return repository.findAll();
    }


    public EquipamentoEntity buscarPorId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
    }

    public EquipamentoEntity atualizar(UUID id, EquipamentoEntity equipamento) {
        if (id == null || equipamento == null) {
            throw new IllegalArgumentException("ID e equipamento não podem ser nulos");
        }
        if (equipamento.getTipo() == null || equipamento.getTipo().isBlank()) {
            throw new IllegalArgumentException("Tipo não pode ser vazio");
        }

        EquipamentoEntity existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        existente.setTipo(equipamento.getTipo());
        existente.setTemperaturaAtual(equipamento.getTemperaturaAtual());
        existente.setTemperaturaIdeal(equipamento.getTemperaturaIdeal());
        existente.setFichaTecnica(equipamento.getFichaTecnica());

        return repository.save(existente);
    }

    public void deletar(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        EquipamentoEntity equipamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        repository.delete(equipamento);
    }
}

