package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.FichaTecnicaResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.FichaTecnicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "Criar ficha técnica", description = "Cria uma nova ficha técnica. Apenas ADMIN ou GERENTE podem criar.") // <-- adicionar
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ficha técnica criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FichaTecnicaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PostMapping
    public ResponseEntity<FichaTecnicaResponseDTO> criar(
            @Valid @RequestBody FichaTecnicaRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criar(dto));
    }

    // LISTAR - todos podem acessar
    @Operation(summary = "Listar fichas técnicas", description = "Lista todas as fichas técnicas disponíveis.") // <-- adicionar
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de fichas técnicas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FichaTecnicaResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<FichaTecnicaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ATUALIZAR - apenas ADMIN ou GERENTE
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @Operation(summary = "Atualizar ficha técnica", description = "Atualiza os dados de uma ficha técnica existente.") // <-- adicionar
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ficha técnica atualizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FichaTecnicaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ficha técnica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FichaTecnicaResponseDTO> atualizar(
            @PathVariable UUID id, @Valid @RequestBody FichaTecnicaRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // DELETAR - apenas ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar ficha técnica", description = "Deleta uma ficha técnica existente. Apenas ADMIN pode deletar.") // <-- adicionar
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ficha técnica deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ficha técnica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}