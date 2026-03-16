package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.service.EquipamentoService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {
    private final EquipamentoService service;

    public EquipamentoController(EquipamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EquipamentoEntity> criar(
            @Valid @RequestBody EquipamentoEntity equipamento) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criar(equipamento));
    }

    @GetMapping
    public ResponseEntity<List<EquipamentoEntity>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoEntity> buscar(@PathVariable UUID id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoEntity> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody EquipamentoEntity equipamento) {

        return ResponseEntity.ok(service.atualizar(id, equipamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}

