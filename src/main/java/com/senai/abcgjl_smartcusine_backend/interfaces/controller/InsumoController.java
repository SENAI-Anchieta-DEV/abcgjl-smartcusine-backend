package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.dto.InsumoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.InsumoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.InsumoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/insumo")
public class InsumoController {

    private final InsumoService service;

    public InsumoController(InsumoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InsumoResponseDTO> criar(
            @RequestBody InsumoRequestDTO dto){

        return ResponseEntity.ok(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<InsumoResponseDTO>> listar(){

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsumoResponseDTO> buscarPorId(
            @PathVariable UUID id){

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumoResponseDTO> atualizar(
            @PathVariable UUID id,
            @RequestBody InsumoRequestDTO dto){

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable UUID id){

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
