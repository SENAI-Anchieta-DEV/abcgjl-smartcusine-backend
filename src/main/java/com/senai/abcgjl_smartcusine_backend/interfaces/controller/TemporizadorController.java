package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.dto.TemporizadorDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.TemporizadorService;
import com.senai.abcgjl_smartcusine_backend.interfaces.documentation.ErrorResponseSchema;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/temporizadores")
public class TemporizadorController {
    private final TemporizadorService temporizadorService;

    public TemporizadorController(TemporizadorService temporizadorService) {
        this.temporizadorService = temporizadorService;
    }
    @Operation(summary = "Cria um novo temporizador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Temporizador criado com sucesso",
                    content = @Content(schema = @Schema(implementation = TemporizadorDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class))
            )
    })
    @PostMapping
    public ResponseEntity<TemporizadorDTO> criar(@Valid @RequestBody TemporizadorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(temporizadorService.criar(dto));
    }

    @Operation(summary = "Lista todos os temporizadores") //
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = TemporizadorDTO.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<TemporizadorDTO>> listarTodos() {
        return ResponseEntity.ok(temporizadorService.listarTodos());
    }

    @Operation(summary = "Busca um temporizador pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temporizador encontrado",
                    content = @Content(schema = @Schema(implementation = TemporizadorDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Temporizador não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class))) // TODO: adicionar schema de erro
                    })
    @GetMapping("/{id}")
    public ResponseEntity<TemporizadorDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(temporizadorService.buscarPorId(id));
    }

    @Operation(summary = "Atualiza um temporizador existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temporizador atualizado com sucesso",
                   content = @Content(schema = @Schema(implementation = TemporizadorDTO.class))),

            @ApiResponse(responseCode = "404", description = "Temporizador não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class)) // TODO: adicionar schema de erro
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<TemporizadorDTO> atualizar(@Valid @PathVariable UUID id, @RequestBody TemporizadorDTO dto) {
        return ResponseEntity.ok(temporizadorService.atualizar(id, dto));
    }

    @Operation(summary = "Deleta um temporizador pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Temporizador deletado com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Temporizador não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class)) // TODO: adicionar schema de erro)
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        temporizadorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
