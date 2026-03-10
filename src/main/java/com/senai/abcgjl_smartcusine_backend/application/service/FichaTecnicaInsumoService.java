package com.senai.abcgjl_smartcusine_backend.application.service;

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

    @Autowired
    private FichaTecnicaInsumoRepository fichaTecnicaInsumoRepository;

    @Autowired
    private FichaTecnicaRepository fichaTecnicaRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    public FichaTecnicaInsumoEntity adicionarInsumo(UUID idFicha, UUID idInsumo, Double quantidade){

        FichaTecnicaEntity ficha = fichaTecnicaRepository.findById(idFicha)
                .orElseThrow(() -> new RuntimeException("Ficha técnica não encontrada"));

        InsumoEntity insumo = insumoRepository.findById(idInsumo)
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado"));

        FichaTecnicaInsumoEntity relacao = new FichaTecnicaInsumoEntity();

        relacao.setFichaTecnica(ficha);
        relacao.setInsumo(insumo);
        relacao.setQuantidade(quantidade);

        return fichaTecnicaInsumoRepository.save(relacao);

    }

    public List<FichaTecnicaInsumoResponseDTO> listar(){

        return fichaTecnicaInsumoRepository.findAll()
                .stream()
                .map(FichaTecnicaInsumoMapper::toDTO)
                .toList();
    }

    public void deletar(UUID id){
        fichaTecnicaInsumoRepository.deleteById(id);
    }

    public FichaTecnicaInsumoEntity atualizarQuantidade(UUID id, Double quantidade){

        FichaTecnicaInsumoEntity relacao = fichaTecnicaInsumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relação não encontrada"));

        relacao.setQuantidade(quantidade);

        return fichaTecnicaInsumoRepository.save(relacao);
    }

}
