package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaInsumoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaInsumoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.FichaTecnicaInsumoService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaInsumoEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Adicionar insumo à ficha técnica")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Insumo adicionado com sucesso",
                    content = @Content(schema = @Schema(implementation = FichaTecnicaInsumoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
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

    @Operation(summary = "Listar todos os insumos da ficha técnica")
    @GetMapping
    public ResponseEntity<List<FichaTecnicaInsumoResponseDTO>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Atualizar um insumo da ficha técnica")
    @PutMapping("/{id}")
    public ResponseEntity<FichaTecnicaInsumoResponseDTO> atualizar(
            @PathVariable UUID id,
            @RequestBody FichaTecnicaInsumoRequestDTO dto){

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Remover um insumo da ficha técnica")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}


