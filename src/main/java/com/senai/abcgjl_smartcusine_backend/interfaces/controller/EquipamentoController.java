package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.dto.EquipamentoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.EquipamentoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.EquipamentoService;
import com.senai.abcgjl_smartcusine_backend.interfaces.documentation.ErrorResponseSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
            @ApiResponse(responseCode = "201", description = "Equipamento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = EquipamentoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class)))
    })
    @PostMapping
    public ResponseEntity<EquipamentoResponseDTO> criar(@Valid @RequestBody EquipamentoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @Operation(summary = "Listar equipamentos", description = "Retorna todos os equipamentos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = EquipamentoResponseDTO.class))))
    @GetMapping
    public ResponseEntity<List<EquipamentoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar equipamento por ID", description = "Retorna um equipamento específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipamento encontrado",
                    content = @Content(schema = @Schema(implementation = EquipamentoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Atualizar equipamento", description = "Atualiza os dados de um equipamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipamento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = EquipamentoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class))),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> atualizar(@PathVariable UUID id,
                                                            @Valid @RequestBody EquipamentoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Deletar equipamento", description = "Remove um equipamento pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipamento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
