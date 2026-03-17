package com.senai.abcgjl_smartcusine_backend.application.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.mapper.FichaTecnicaMapper;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import com.senai.abcgjl_smartcusine_backend.domain.exception.FichaTecnicaNaoEncontradaException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.FichaTecnicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FichaTecnicaService {

    private final FichaTecnicaRepository repository;

    public FichaTecnicaService(FichaTecnicaRepository repository) {
        this.repository = repository;
    }

    public FichaTecnicaResponseDTO criar(FichaTecnicaRequestDTO dto) {

        if(repository.existsByNomePreparo(dto.getNomePreparo())){
            throw new RuntimeException("Já existe uma ficha técnica com esse nome");
        }

        FichaTecnicaEntity ficha = FichaTecnicaMapper.toEntity(dto);

        FichaTecnicaEntity salva = repository.save(ficha);

        return FichaTecnicaMapper.toResponseDTO(salva);
    }

    public List<FichaTecnicaResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(FichaTecnicaMapper::toResponseDTO)
                .toList();
    }

    public FichaTecnicaResponseDTO atualizar(UUID id, FichaTecnicaRequestDTO dto) {

        FichaTecnicaEntity ficha = repository.findById(id)
                .orElseThrow(() -> new FichaTecnicaNaoEncontradaException());

        if(repository.existsByNomePreparo(dto.getNomePreparo()) &&
                !ficha.getNomePreparo().equals(dto.getNomePreparo())){

            throw new RuntimeException("Já existe uma ficha técnica com esse nome");
        }

        ficha.setNomePreparo(dto.getNomePreparo());
        ficha.setTempoIdeal(dto.getTempoIdeal());
        ficha.setTemperaturaIdeal(dto.getTemperaturaIdeal());

        FichaTecnicaEntity atualizada = repository.save(ficha);

        return FichaTecnicaMapper.toResponseDTO(atualizada);
    }

    public void deletar(UUID id) {

        FichaTecnicaEntity ficha = repository.findById(id)
                .orElseThrow(() -> new FichaTecnicaNaoEncontradaException());

        repository.delete(ficha);
    }
}