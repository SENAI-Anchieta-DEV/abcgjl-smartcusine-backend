package com.senai.abcgjl_smartcusine_backend.application.dto;

import com.senai.abcgjl_smartcusine_backend.domain.enums.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class UsuarioRequestDTO {

    @Schema(description = "Nome completo do usuário", example = "João da Silva",  required = true)
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Schema(description = "Email do usuário", example = "joao@email.com",  required = true)
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @Schema(description = "Senha do usuário (mínimo 6 caracteres)", example = "123456",  required = true)
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    @Schema(description = "Tipo do usuário (ADMIN, GERENTE, COZINHEIRO)", example = "ADMIN", required = true )
    @NotNull(message = "O tipo de usuário é obrigatório")
    private TipoUsuario tipo;


    public UsuarioRequestDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

}
