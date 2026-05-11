package com.senai.abcgjl_smartcusine_backend.application.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.InsumoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.InsumoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.mapper.InsumoMapper;
import com.senai.abcgjl_smartcusine_backend.domain.entity.InsumoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.exception.InsumoNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.InsumoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InsumoService {

    private final InsumoRepository repository;
    private final InsumoMapper mapper;

    public InsumoService(InsumoRepository repository, InsumoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public InsumoResponseDTO criar(InsumoRequestDTO dto){
        if(repository.existsByNome(dto.nome())){
            throw new RuntimeException("Já existe um insumo com esse nome");
        }

        InsumoEntity entity = mapper.toEntity(dto);

        entity.setQrCode("INSUMO-" + UUID.randomUUID());

        repository.save(entity);

        return mapper.toResponse(entity);
    }

    public List<InsumoResponseDTO> listar(){

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public InsumoResponseDTO buscarPorId(UUID id){

        InsumoEntity entity = repository.findById(id)
                .orElseThrow(() -> new InsumoNaoEncontradoException());

        return mapper.toResponse(entity);
    }

    public InsumoResponseDTO atualizar(UUID id, InsumoRequestDTO dto){

        InsumoEntity entity = repository.findById(id)
                .orElseThrow(() -> new InsumoNaoEncontradoException());

        if(repository.existsByNome(dto.nome()) && !entity.getNome().equals(dto.nome())){
            throw new RuntimeException("Já existe um insumo com esse nome");
        }

        entity.setNome(dto.nome());
        entity.setUnidadeMedida(dto.unidadeMedida());
        entity.setQuantidadeEstoque(dto.quantidadeEstoque());
        entity.setDataValidade(dto.dataValidade());

        repository.save(entity);

        return mapper.toResponse(entity);
    }

    public void deletar(UUID id){

        InsumoEntity entity = repository.findById(id)
                .orElseThrow(() -> new InsumoNaoEncontradoException());

        repository.delete(entity);
    }
}
