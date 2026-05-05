package com.senai.abcgjl_smartcusine_backend.application.service;

import com.senai.abcgjl_smartcusine_backend.application.dto.UsuarioRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.UsuarioResponseDTO;
import com.senai.abcgjl_smartcusine_backend.application.mapper.UsuarioMapper;
import com.senai.abcgjl_smartcusine_backend.domain.entity.UsuarioEntity;
import com.senai.abcgjl_smartcusine_backend.domain.enums.TipoUsuario;
import com.senai.abcgjl_smartcusine_backend.domain.exception.EmailJaCadastradoException;
import com.senai.abcgjl_smartcusine_backend.domain.exception.UsuarioNaoEncontradoException;
import com.senai.abcgjl_smartcusine_backend.domain.UsuarioRepository;
import com.senai.abcgjl_smartcusine_backend.interfaces.exception.AcessoNegadoException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    private UsuarioEntity getUsuarioLogado() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());
    }

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO dto) {

        if (dto.getTipo() == null) {
            throw new IllegalArgumentException("Tipo de usuário obrigatório");
        }

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

        UsuarioEntity usuarioLogado = getUsuarioLogado();

        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        if (usuarioLogado.getTipo() == TipoUsuario.GERENTE &&
                usuario.getTipo() == TipoUsuario.ADMIN) {

            throw new AcessoNegadoException("Gerente não pode deletar ADMIN");
        }

        usuarioRepository.delete(usuario);
    }

    public UsuarioResponseDTO buscarPorId(Long id) {

        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        return UsuarioMapper.toResponseDTO(usuario);
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto) {

        UsuarioEntity usuarioLogado = getUsuarioLogado();

        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        // 🔒 REGRA DE NEGÓCIO
        if (usuarioLogado.getTipo() == TipoUsuario.GERENTE &&
                usuario.getTipo() == TipoUsuario.ADMIN) {

            throw new AcessoNegadoException("Gerente não pode alterar ADMIN");
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
