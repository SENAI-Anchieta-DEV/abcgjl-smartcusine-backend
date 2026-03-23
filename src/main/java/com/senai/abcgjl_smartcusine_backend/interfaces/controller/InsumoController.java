package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.dto.InsumoRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.InsumoResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.InsumoService;
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
@RequestMapping("/insumos")
public class InsumoController {

    private final InsumoService service;

    public InsumoController(InsumoService service) {
        this.service = service;
    }


    @Operation(summary = "Cria um novo insumo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Insumo criado com sucesso",
                    content = @Content(schema = @Schema(implementation = InsumoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class)) // TODO: adicionar schema de erro
            )
    })
        @PostMapping
    public ResponseEntity<InsumoResponseDTO> criar(
            @Valid @RequestBody InsumoRequestDTO dto){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criar(dto));
    }

    @Operation(summary = "Lista todos os insumos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de insumos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = InsumoResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<InsumoResponseDTO>> listar(){

        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Busca um insumo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Insumo encontrado",
                    content = @Content(schema = @Schema(implementation = InsumoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Insumo não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class)) // TODO: adicionar schema de erro
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<InsumoResponseDTO> buscarPorId(
            @PathVariable UUID id){

        return ResponseEntity.ok(service.buscarPorId(id));
    }


    @Operation(summary = "Atualiza um insumo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Insumo atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = InsumoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",  content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class)) // TODO: adicionar schema de erro
            ),
            @ApiResponse(responseCode = "404", description = "Insumo não encontrado",  content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class)) // TODO: adicionar schema de erro
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<InsumoResponseDTO> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody InsumoRequestDTO dto){

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Deleta um insumo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Insumo deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Insumo não encontrado",  content = @Content(schema = @Schema(implementation = ErrorResponseSchema.class)) // TODO: adicionar schema de erro
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable UUID id){

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
