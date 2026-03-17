package com.senai.abcgjl_smartcusine_backend.application.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.UsuarioRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.UsuarioResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.mapper.UsuarioMapper;
import com.senai.abcgjl_smartcusine_backend.domain.entity.UsuarioEntity;
import com.senai.abcgjl_smartcusine_backend.domain.exception.EmailJaCadastradoException;
import com.senai.abcgjl_smartcusine_backend.domain.exception.UsuarioNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO dto) {

        if (dto.getSenha().length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres");
        }

        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailJaCadastradoException();
        }

        UsuarioEntity usuario = UsuarioMapper.toEntity(dto);

        // 🔐 criptografa a senha
        usuario.setSenha(encoder.encode(dto.getSenha()));

        UsuarioEntity salvo = usuarioRepository.save(usuario);

        return UsuarioMapper.toResponseDTO(salvo);
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void deletarUsuario(Long id) {

        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        usuarioRepository.delete(usuario);
    }

    public UsuarioResponseDTO buscarPorId(Long id) {

        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        return UsuarioMapper.toResponseDTO(usuario);
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto) {

        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        // verifica se o email já existe em outro usuário
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent() &&
                !usuario.getEmail().equals(dto.getEmail())) {
            throw new EmailJaCadastradoException();
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(encoder.encode(dto.getSenha()));
        }

        usuario.setTipo(dto.getTipo());

        UsuarioEntity atualizado = usuarioRepository.save(usuario);

        return UsuarioMapper.toResponseDTO(atualizado);
    }
}
