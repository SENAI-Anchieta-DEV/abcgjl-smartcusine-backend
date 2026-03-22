package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.service.EquipamentoService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.EquipamentoEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Cadastrar equipamento", description = "Cria um novo equipamento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<EquipamentoEntity> criar(
            @Valid @RequestBody EquipamentoEntity equipamento) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criar(equipamento));
    }

    @Operation(summary = "Listar equipamentos", description = "Retorna todos os equipamentos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<EquipamentoEntity>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar equipamento por ID", description = "Retorna um equipamento específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoEntity> buscar(@PathVariable UUID id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Atualizar equipamento", description = "Atualiza os dados de um equipamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipamento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoEntity> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody EquipamentoEntity equipamento) {

        return ResponseEntity.ok(service.atualizar(id, equipamento));
    }

    @Operation(summary = "Deletar equipamento", description = "Remove um equipamento pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipamento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}

