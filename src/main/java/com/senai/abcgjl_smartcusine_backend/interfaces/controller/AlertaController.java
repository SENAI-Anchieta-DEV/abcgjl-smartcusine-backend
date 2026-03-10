package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.dto.AlertaResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.AlertaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alerta")
public class AlertaController {

    private final AlertaService service;

    public AlertaController(AlertaService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AlertaResponseDTO>> listar(){

        return ResponseEntity.ok(service.listar());
    }

    // DELETAR - apenas ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
