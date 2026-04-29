package com.senai.abcgjl_smartcusine_backend.application.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaInsumoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaInsumoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.mapper.FichaTecnicaInsumoMapper;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaInsumoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.entity.InsumoEntity;
import com.senai.abcgjl_smartcusine_backend.domain.repository.FichaTecnicaInsumoRepository;
import com.senai.abcgjl_smartcusine_backend.domain.repository.FichaTecnicaRepository;
import com.senai.abcgjl_smartcusine_backend.domain.repository.InsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FichaTecnicaInsumoService {

    private final FichaTecnicaInsumoRepository fichaTecnicaInsumoRepository;
    private final FichaTecnicaRepository fichaTecnicaRepository;
    private final InsumoRepository insumoRepository;

    public FichaTecnicaInsumoService(
            FichaTecnicaInsumoRepository fichaTecnicaInsumoRepository,
            FichaTecnicaRepository fichaTecnicaRepository,
            InsumoRepository insumoRepository) {

        this.fichaTecnicaInsumoRepository = fichaTecnicaInsumoRepository;
        this.fichaTecnicaRepository = fichaTecnicaRepository;
        this.insumoRepository = insumoRepository;
    }

    public FichaTecnicaInsumoResponseDTO adicionarInsumo(UUID idFicha, UUID idInsumo, Double quantidade){
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }

        if(fichaTecnicaInsumoRepository.existsByFichaTecnicaIdAndInsumoId(idFicha, idInsumo)){
            throw new RuntimeException("Este insumo já foi adicionado a esta ficha técnica");
        }

        FichaTecnicaEntity ficha = fichaTecnicaRepository.findById(idFicha)
                .orElseThrow(() -> new RuntimeException("Ficha técnica não encontrada"));

        InsumoEntity insumo = insumoRepository.findById(idInsumo)
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado"));

        FichaTecnicaInsumoEntity relacao = new FichaTecnicaInsumoEntity();

        relacao.setFichaTecnica(ficha);
        relacao.setInsumo(insumo);
        relacao.setQuantidade(quantidade);

        FichaTecnicaInsumoEntity salva = fichaTecnicaInsumoRepository.save(relacao);

        return FichaTecnicaInsumoMapper.toDTO(salva);

    }

    public List<FichaTecnicaInsumoResponseDTO> listar(){

        return fichaTecnicaInsumoRepository.findAll()
                .stream()
                .map(FichaTecnicaInsumoMapper::toDTO)
                .toList();
    }

    public void deletar(UUID id){
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        if (!fichaTecnicaInsumoRepository.existsById(id)) {
            throw new RuntimeException("Relação não encontrada");
        }
        fichaTecnicaInsumoRepository.deleteById(id);
    }

    public FichaTecnicaInsumoResponseDTO atualizar(UUID id, FichaTecnicaInsumoRequestDTO dto){
        if (dto == null || dto.quantidade() == null || dto.quantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }

        FichaTecnicaInsumoEntity relacao = fichaTecnicaInsumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relação não encontrada"));

        relacao.setQuantidade(dto.quantidade());

        FichaTecnicaInsumoEntity salva = fichaTecnicaInsumoRepository.save(relacao);

        return FichaTecnicaInsumoMapper.toDTO(salva);
    }

}
