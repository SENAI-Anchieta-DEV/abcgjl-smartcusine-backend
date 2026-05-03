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

        if (dto == null) {
            throw new IllegalArgumentException("DTO não pode ser nulo");
        }

        if (dto.getNomePreparo() == null || dto.getNomePreparo().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

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

        // ✅ 1. VALIDAÇÕES BÁSICAS
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        if (dto == null) {
            throw new IllegalArgumentException("DTO não pode ser nulo");
        }

        // ✅ 2. BUSCA PRIMEIRO (ANTES DE QUALQUER get do DTO)
        FichaTecnicaEntity ficha = repository.findById(id)
                .orElseThrow(FichaTecnicaNaoEncontradaException::new);

        // ✅ 3. AGORA SIM valida conteúdo
        String nome = dto.getNomePreparo();

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        // ✅ 4. REGRA DE NEGÓCIO
        if (repository.existsByNomePreparo(nome) &&
                !ficha.getNomePreparo().equals(nome)) {

            throw new RuntimeException("Já existe uma ficha técnica com esse nome");
        }

        ficha.setNomePreparo(nome);
        ficha.setTempoIdeal(dto.getTempoIdeal());
        ficha.setTemperaturaIdeal(dto.getTemperaturaIdeal());

        return FichaTecnicaMapper.toResponseDTO(repository.save(ficha));
    }
    public void deletar(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        FichaTecnicaEntity ficha = repository.findById(id)
                .orElseThrow(() -> new FichaTecnicaNaoEncontradaException());

        repository.delete(ficha);
    }
}