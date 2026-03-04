package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.*;

import java.util.UUID;

    @Entity
    @Table(name = "usuarios")
    @Inheritance(strategy = InheritanceType.JOINED)
    public abstract class Usuario {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID idUsuario;

        private String nome;
        private String cpf;
        private String senha;

        protected Usuario() {}

        public Usuario(String nome, String cpf, String senha) {
            this.nome = nome;
            this.cpf = cpf;
            this.senha = senha;
        }

        public void login() {

        }

        public void logout() {

        }

        public UUID getIdUsuario() {
            return idUsuario;
        }
    }

