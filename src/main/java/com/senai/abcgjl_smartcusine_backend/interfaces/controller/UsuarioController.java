package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import com.senai.abcgjl_smartcusine_backend.application.dto.UsuarioRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.UsuarioResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.service.UsuarioService;
import com.senai.abcgjl_smartcusine_backend.domain.entity.UsuarioEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody UsuarioRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.cadastrarUsuario(dto));
    }
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @Valid @PathVariable Long id,
            @RequestBody UsuarioRequestDTO dto) {

        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, dto));
    }

}



