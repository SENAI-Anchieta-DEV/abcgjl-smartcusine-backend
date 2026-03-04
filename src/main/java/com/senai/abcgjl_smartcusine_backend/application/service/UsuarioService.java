package com.senai.abcgjl_smartcusine_backend.application.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.UsuarioRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.UsuarioResponseDTO;
import com.senai.abcgjl_smartcusine_backend.domain.entity.UsuarioEntity;
import com.senai.abcgjl_smartcusine_backend.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO dto) {

        // Verifica se email já existe
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado.");
        }

        // Converte DTO -> Entity
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setTipo(dto.getTipo());

        UsuarioEntity salvo = usuarioRepository.save(usuario);


        return new UsuarioResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail(),
                salvo.getTipo()
        );
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getTipo()
                ))
                .collect(Collectors.toList());
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);

    }


    public UsuarioResponseDTO buscarPorId(Long id) {

        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipo()
        );
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto) {

        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setTipo(dto.getTipo());

        UsuarioEntity atualizado = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                atualizado.getId(),
                atualizado.getNome(),
                atualizado.getEmail(),
                atualizado.getTipo()
        );

    }
}
