package com.senai.abcgjl_smartcusine_backend.application.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.AlertaResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.mapper.AlertaMapper;
import com.senai.abcgjl_smartcusine_backend.domain.exception.AlertaNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.AlertaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AlertaService {

    private final AlertaRepository repository;

    public AlertaService(AlertaRepository repository) {
        this.repository = repository;
    }

    public List<AlertaResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(AlertaMapper::toDTO)
                .toList();
    }

    public void deletar(UUID id) {

        if (!repository.existsById(id)) {
            throw new AlertaNaoEncontradoException();
        }

        repository.deleteById(id);
    }
}
