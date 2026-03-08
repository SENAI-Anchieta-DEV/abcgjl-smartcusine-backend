package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.FichaTecnicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fichas-tecnicas")
public class FichaTecnicaController {

    private final FichaTecnicaService service;

    public FichaTecnicaController(FichaTecnicaService service) {
        this.service = service;
    }

    // CRIAR - apenas ADMIN ou GERENTE
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @PostMapping
    public ResponseEntity<FichaTecnicaResponseDTO> criar(@RequestBody FichaTecnicaRequestDTO dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    // LISTAR - todos podem acessar
    @GetMapping
    public ResponseEntity<List<FichaTecnicaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ATUALIZAR - apenas ADMIN ou GERENTE
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<FichaTecnicaResponseDTO> atualizar(
            @PathVariable UUID id, @RequestBody FichaTecnicaRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // DELETAR - apenas ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}