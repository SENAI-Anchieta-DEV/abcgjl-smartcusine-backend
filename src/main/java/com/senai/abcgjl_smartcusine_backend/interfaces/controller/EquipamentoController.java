package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.service.EquipamentoService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
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
    public EquipamentoEntity criar(@RequestBody EquipamentoEntity equipamento) {
        return service.criar(equipamento);
    }

    @GetMapping
    public List<EquipamentoEntity> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public EquipamentoEntity buscar(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public EquipamentoEntity atualizar(@PathVariable UUID id, @RequestBody EquipamentoEntity equipamento) {
        return service.atualizar(id, equipamento);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }
}

