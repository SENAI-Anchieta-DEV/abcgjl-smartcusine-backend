package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaInsumoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaInsumoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.FichaTecnicaInsumoService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaInsumoEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ficha-tecnica-insumos")
public class FichaTecnicaInsumoController {

    private final FichaTecnicaInsumoService service;

    public FichaTecnicaInsumoController(FichaTecnicaInsumoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FichaTecnicaInsumoResponseDTO> adicionarInsumo(
            @Valid @RequestBody FichaTecnicaInsumoRequestDTO dto){

        FichaTecnicaInsumoResponseDTO response = service.adicionarInsumo(
                dto.fichaTecnicaId(),
                dto.insumoId(),
                dto.quantidade()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<FichaTecnicaInsumoResponseDTO>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FichaTecnicaInsumoResponseDTO> atualizar(
            @PathVariable UUID id,
            @RequestBody FichaTecnicaInsumoRequestDTO dto){

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}


