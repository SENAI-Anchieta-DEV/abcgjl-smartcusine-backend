package com.senai.abcgjl_smartcusine_backend.application.mapper;

import com.senai.abcgjl_smartcusine_backend.application.dto.UsuarioRequestDTO;
import com.senai.abcgjl_smartcusine_backend.application.dto.UsuarioResponseDTO;
import com.senai.abcgjl_smartcusine_backend.domain.entity.UsuarioEntity;

public class UsuarioMapper {

    public static UsuarioEntity toEntity(UsuarioRequestDTO dto) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setTipo(dto.getTipo());
        return usuario;
    }

    public static UsuarioResponseDTO toResponseDTO(UsuarioEntity entity) {
        return new UsuarioResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getTipo()
        );
    }
}
