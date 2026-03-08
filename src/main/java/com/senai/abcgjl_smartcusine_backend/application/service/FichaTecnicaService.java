package com.senai.abcgjl_smartcusine_backend.application.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.mapper.FichaTecnicaMapper;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import com.senai.abcgjl_smartcusine_backend.domain.repository.FichaTecnicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FichaTecnicaService {

    private final FichaTecnicaRepository repository;

    public FichaTecnicaService(FichaTecnicaRepository repository) {
        this.repository = repository;
    }

    public FichaTecnicaResponseDTO criar(FichaTecnicaRequestDTO dto){

        FichaTecnicaEntity ficha = FichaTecnicaMapper.toEntity(dto);

        FichaTecnicaEntity salva = repository.save(ficha);

        return FichaTecnicaMapper.toResponseDTO(salva);
    }

    public List<FichaTecnicaResponseDTO> listar(){
        return repository.findAll()
                .stream()
                .map(FichaTecnicaMapper::toResponseDTO)
                .toList();
    }

    public void deletar(UUID id){
        repository.deleteById(id);
    }
}
