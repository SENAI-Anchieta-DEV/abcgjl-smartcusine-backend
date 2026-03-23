package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.dto.AlertaResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.AlertaService;
import com.senai.abcgjl_smartcusine_backend.interfaces.documentation.ErrorResponseSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Listar alertas", description = "Retorna todos os alertas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = AlertaResponseDTO.class)))

    @GetMapping
    public ResponseEntity<List<AlertaResponseDTO>> listar(){

        return ResponseEntity.ok(service.listar());
    }

    // DELETAR - apenas ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar alerta", description = "Deleta um alerta pelo ID (somente ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alerta deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
