package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.dto.TemporizadorDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.TemporizadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/temporizadores")
public class TemporizadorController {
    private final TemporizadorService temporizadorService;

    public TemporizadorController(TemporizadorService temporizadorService) {
        this.temporizadorService = temporizadorService;
    }

    @PostMapping
    public ResponseEntity<TemporizadorDTO> criar(@Valid @RequestBody TemporizadorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(temporizadorService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<TemporizadorDTO>> listarTodos() {
        return ResponseEntity.ok(temporizadorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemporizadorDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(temporizadorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemporizadorDTO> atualizar(@Valid @PathVariable UUID id, @RequestBody TemporizadorDTO dto) {
        return ResponseEntity.ok(temporizadorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        temporizadorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
