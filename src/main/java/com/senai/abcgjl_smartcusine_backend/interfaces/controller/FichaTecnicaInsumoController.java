package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaInsumoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.FichaTecnicaInsumoService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaInsumoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ficha-tecnica-insumos")
public class FichaTecnicaInsumoController {

    @Autowired
    private FichaTecnicaInsumoService service;

    @PostMapping
    public FichaTecnicaInsumoEntity adicionarInsumo(
            @RequestParam UUID idFicha,
            @RequestParam UUID idInsumo,
            @RequestParam Double quantidade){

        return service.adicionarInsumo(idFicha, idInsumo, quantidade);
    }

    @GetMapping
    public List<FichaTecnicaInsumoResponseDTO> listar(){
        return service.listar();
    }

    @PutMapping("/{id}")
    public FichaTecnicaInsumoEntity atualizarQuantidade(
            @PathVariable UUID id,
            @RequestParam Double quantidade){
        return service.atualizarQuantidade(id, quantidade);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id){
        service.deletar(id);
    }
}


